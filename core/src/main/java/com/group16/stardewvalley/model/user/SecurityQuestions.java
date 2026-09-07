package com.group16.stardewvalley.model.user;

public enum SecurityQuestions {
    q1(1, "What was the name of your imaginary friend?"),
    q2(2, "If you were a cartoon character, who would you be?"),
    q3(3, "If you were a fruit, what fruit would you be?"),
    q4(4, "If you had a pet dragon, what would you name it?"),
    q5(5, "What sandwich would you fight a raccoon over?");

    final int number;
    final String question;

    SecurityQuestions(int number, String question) {
        this.number = number;
        this.question = question;
    }

    public int getNumber() {
        return number;
    }

    public String getQuestion() {
        return question;
    }

    public static SecurityQuestions fromNumber(int number) {
        for (SecurityQuestions q : values()) {
            if (q.getNumber() == number) return q;
        }
        return null; // Not found
    }

}
