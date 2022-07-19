package org.webview1;

public class Question {

    int id, right;
    String text;
    String[] answers;
    public Question(int id,int right, String text, String[] answers) {
        this.id = id;
        this.answers = answers;
        this.right = right;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
}
