package com.korchagin;

import java.util.Scanner;

public class Main {
    private static final String[] ROMAN_NUMERALS = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
    private static final int[] ROMAN_VALUES = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

    public static void main(String[] args) {
        System.out.println("Введите данные в формате: <число1> <оператор> <число2>");
        Scanner input = new Scanner(System.in);
        String output = calc(input.nextLine());
        System.out.printf(output);
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат ввода!");
        }

        String number1 = parts[0];
        String operator = parts[1];
        String number2 = parts[2];

        boolean isRomanNumeral1 = isRomanNumeral(number1);
        boolean isRomanNumeral2 = isRomanNumeral(number2);
        if (isRomanNumeral1 != isRomanNumeral2) {
            throw new IllegalArgumentException("Используются разные системы счисления!");
        }

        int num1, num2;

        if (isRomanNumeral1) {
            num1 = romanToArabic(number1);
            num2 = romanToArabic(number2);
        } else {
            num1 = Integer.parseInt(number1);
            num2 = Integer.parseInt(number2);
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Числа должны быть от 1 до 10");
        }

        int result = switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new IllegalArgumentException("Недопустимый оператор!");
        };

        String formattedResult;
        if (isRomanNumeral1) {
            formattedResult = arabicToRoman(result);
        } else {
            formattedResult = String.valueOf(result);
        }

        return formattedResult;
    }

    private static boolean isRomanNumeral(String input) {
        String romanNumerals = "IVXLCDM";
        for (char c : input.toCharArray()) {
            if (!romanNumerals.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    private static int romanToArabic(String input) {
        int result = 0;
        int i = 0;

        while (i < input.length()) {
            char currentSymbol = input.charAt(i);
            int currentValue = romanNumeralValue(currentSymbol);

            if ((i + 1) < input.length()) {
                char nextSymbol = input.charAt(i + 1);
                int nextValue = romanNumeralValue(nextSymbol);

                if (currentValue >= nextValue) {
                    result += currentValue;
                    i++;
                } else {
                    result += (nextValue - currentValue);
                    i += 2;
                }
            } else {
                result += currentValue;
                i++;
            }
        }

        return result;
    }

    private static String arabicToRoman(int number) {
        StringBuilder romanNumeral = new StringBuilder();

        for (int i = ROMAN_VALUES.length - 1; i >= 0; i--) {
            while (number >= ROMAN_VALUES[i]) {
                romanNumeral.append(ROMAN_NUMERALS[i]);
                number -= ROMAN_VALUES[i];
            }
        }

        return romanNumeral.toString();
    }

    private static int romanNumeralValue(char romanSymbol) {
        return switch (romanSymbol) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> throw new IllegalArgumentException("Неверный символ римской цифры!");
        };
    }
}