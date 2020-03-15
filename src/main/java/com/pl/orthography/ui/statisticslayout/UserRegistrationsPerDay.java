package com.pl.orthography.ui.statisticslayout;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.grid.builder.RowBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.helper.Series;
import com.pl.orthography.service.UserService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class UserRegistrationsPerDay extends VerticalLayout {

    private HorizontalLayout horizontalLayout;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private Button generateChartButton;
    private ApexCharts lineChart;

    private UserService userService;

    public UserRegistrationsPerDay(UserService userService) {
        this.userService = userService;

        createForm();
        horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(startDatePicker, endDatePicker, generateChartButton);
        horizontalLayout.setVerticalComponentAlignment(FlexComponent.Alignment.END, generateChartButton);
        add(horizontalLayout);
    }

    private void createForm() {
        startDatePicker = createDatePicker("Start date");
        endDatePicker = createDatePicker("End date");
        generateChartButton = createGenerateChartButton();
    }

    private DatePicker createDatePicker(String label) {
        DatePicker datePicker = new DatePicker();

        datePicker.setLabel(label);
        datePicker.setClearButtonVisible(true);
        datePicker.setErrorMessage("Incorrect date");

        return datePicker;
    }

    private Button createGenerateChartButton() {
        Button button = new Button("Generate chart");

        button.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
                startDatePicker.setInvalid(true);
                endDatePicker.setInvalid(true);
                horizontalLayout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, generateChartButton);
            } else {
                startDatePicker.setInvalid(false);
                endDatePicker.setInvalid(false);
                horizontalLayout.setVerticalComponentAlignment(FlexComponent.Alignment.END, generateChartButton);

                generateLineChart(startDate, endDate);
            }
        });

        return button;
    }

    private void generateLineChart(LocalDate startDate, LocalDate endDate) {
        if (lineChart != null) {
            remove(lineChart);
        }

        List<String> categories = getCategories(startDate, endDate);
        Long[] values = getValues(categories);

        createLineChart(categories, values);

        add(lineChart);
    }

    private void createLineChart(List<String> categories, Long[] values) {

        lineChart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.line)
                        .withZoom(ZoomBuilder.get()
                                .withEnabled(true)
                                .build())
                        .build())
                .withStroke(StrokeBuilder.get()
                        .withCurve(Curve.straight)
                        .build())
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("New User by Day")
                        .withAlign(Align.center)
                        .build())
                .withGrid(GridBuilder.get()
                        .withRow(RowBuilder.get()
                                .withColors("#f3f3f3", "transparent")
                                .withOpacity(0.5).build()
                        ).build())
                .withXaxis(XAxisBuilder.get()
                        .withCategories(categories)
                        .build())
                .withSeries(new Series<>("Users", values))
                .build();
    }

    private List<String> getCategories(LocalDate startDate, LocalDate endDate) {
        List<String> categories = new ArrayList<>();

        LocalDate date = startDate;

        while (date.isBefore(endDate) || date.isEqual(endDate)) {
            categories.add(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            date = date.plusDays(1);
        }

        return categories;
    }

    private Long[] getValues(List<String> categories) {
        TreeMap<String, Long> datesToNumberOfRegistration = userService.getDatesToNumberOfRegistration();
        Long[] values = new Long[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            Long numberOfRegistrations = datesToNumberOfRegistration.get(categories.get(i));
            values[i] = numberOfRegistrations == null ? 0 : numberOfRegistrations;
        }

        return values;
    }
}