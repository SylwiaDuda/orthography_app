package com.pl.orthography.ui.statisticslayout;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.pl.orthography.data.entity.AccountStatus;
import com.pl.orthography.service.UserService;
import com.vaadin.flow.component.html.Div;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class AccountsStatistics extends Div {

    private UserService userService;

    public AccountsStatistics(UserService userService) {
        this.userService = userService;

        generateAccountsStateChart();
    }

    private void generateAccountsStateChart() {
        ApexCharts accountsStateChart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get().withType(Type.pie).build())
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("Accounts")
                        .withAlign(Align.center)
                        .build())
                .withLabels("Active account", "Blocked account", "Deleted Account")
                .withLegend(LegendBuilder.get()
                        .withPosition(Position.right)
                        .build())
                .withSeries(prepareSeries())
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .withOptions(OptionsBuilder.get()
                                .withLegend(LegendBuilder.get()
                                        .withPosition(Position.bottom)
                                        .build())
                                .build())
                        .build())
                .build();

        add(accountsStateChart);
    }


    private Double[] prepareSeries() {
        Double[] series = new Double[3];
        Map<AccountStatus, Long> statusToNumberOfAccounts = userService.getStatusToNumberOfAccounts();

        series[0] = getNumberAccountByStatus(statusToNumberOfAccounts, AccountStatus.ACTIVE);
        series[1] = getNumberAccountByStatus(statusToNumberOfAccounts, AccountStatus.BLOCKED);
        series[2] = getNumberAccountByStatus(statusToNumberOfAccounts, AccountStatus.DELETED);

        return series;
    }

    private Double getNumberAccountByStatus(Map<AccountStatus, Long> statusToNumberOfAccounts, AccountStatus status) {
        Long numberOfAccountsWithStatus = statusToNumberOfAccounts.get(status);

        return numberOfAccountsWithStatus == null ? 0.0 : numberOfAccountsWithStatus;
    }
}