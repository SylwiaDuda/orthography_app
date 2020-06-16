package com.pl.orthography.data.datagenerator;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ExampleWords {

    List<String> wordsWithSingleCharacterUO = Arrays.asList(
            "poufały", "łupny", "obrównywać", "kłótliwy",
            "ówczesny", "spauzować", "wtórny", "szutrowy",
            "wózkować", "postukiwać", "szczególny", "skuwka",
            "tabularny", "chóralny", "czubaty", "wójtowy",
            "owadobójczy", "aktualny", "drugi", "różnoraki");
    List<String> wordsWithDoubleCharacterUO = Arrays.asList(
            "równiutki", "futurystyczny", "skrupulatny", "usłużny",
            "próżniutki", "gruboskórny", "czupurny", "augustowski",
            "inaugurować", "kumulować", "kuluarowy", "górotwórczy",
            "punktualny", "cudotwórczy", "burżuazyjny", "uiluminować",
            "muskularny", "subsumować", "rozmunsztukować", "adiutantura ");

    List<String> wordsWithSingleCharacterZRZ = Arrays.asList(
            "kożuch", "rzędowy", "szczeżuja", "wżywać",
            "żółciowy", "barbarzyński", "gorzkawy", "drugorzędny",
            "srebrzysty", "zażegnywać", "wierzący", "rzeczony",
            "zwyżkować", "niedojrzały", "metrażowy", "lonżować",
            "nadrzędny", "nieostrożny", "rzutowy", "odżyłowywać");
    List<String> wordsWithDoubleCharacterZRZ = Arrays.asList(
            "rzeżucha", "żużlowy", "przejrzysty", "przemożny",
            "przyporządkować", "spostrzeżenie", "niesprzedażny", "przerzutny",
            "gżegżółka", "żorżeta", "krzyżacki", "przeżyty",
            "przedmałżeński", "przypowierzchniowy", "pokrzyżować", "lewobrzeżny",
            "przejrzały", "dostrzeżalny", "przełożony", "krzyżówka");

    List<String> wordsWithSingleCharacterHCH = Arrays.asList(
            "wahadło", "chart", "hoży", "hreczka",
            "chalkozyn", "błahy", "pohukiwać", "chyży",
            "holować", "chybotać", "hamburski", "chodak",
            "chuligan", "ohydny", "chrust", "halny",
            "kurhan", "chapać", "hipsometryczny", "mahoniowy");
    List<String> wordsWithDoubleCharacterHCH = Arrays.asList(
            "rozchichotany", "chochlować", "chuchrowaty", "uhierarchizować",
            "hydrotechniczny", "hałłachować", "hachama", "hałdach",
            "chochlik", "uchachany", "hochsztaplera", "chęchy",
            "chudopacholski", "chochla", "harhara", "hydrochinon",
            "chochoł", "chuchać", "hipotrochoida", "chachać");
}