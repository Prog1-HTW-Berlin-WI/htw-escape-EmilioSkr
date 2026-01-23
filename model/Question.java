package model;

import java.io.Serializable;

/**
 * Die Klasse Question stellt eine Multiple-Choice-Frage mit vier Antwortmöglichkeiten dar.
 * 
 * @author Anas
 * @author Emilio
 */
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Die Frage bzw. die Fragestellung
     */
    private String question;
    
    /**
     * Array mit vier Antwortmöglichkeiten (Formate: answers[0] bis answers[3]; answers[0]="(A) ...")
     */
    private String[] answers;
    
    /**
     * Der Index der korrekten Antwort (0-3)
     */
    private int correctAnswerIndex;
    
    /**
     * Konstruktor für eine Question.
     * 
     * @param question Die Frage
     * @param answer1 Erste Antwortmöglichkeit
     * @param answer2 Zweite Antwortmöglichkeit
     * @param answer3 Dritte Antwortmöglichkeit
     * @param answer4 Vierte Antwortmöglichkeit
     * @param correctAnswerIndex Der Index der korrekten Antwort (0-3)
     */
    public Question(String question, String answer1, String answer2, String answer3, String answer4, int correctAnswerIndex) {
        this.question = question;
        this.answers = new String[]{answer1, answer2, answer3, answer4};
        this.correctAnswerIndex = correctAnswerIndex;
    }
    
    /**
     * Gibt die Frage zurück.
     * 
     * @return Die Frage
     */
    public String getQuestion() {
        return question;
    }
    
    /**
     * Gibt die Antwortmöglichkeiten zurück.
     * 
     * @return Array mit vier Antwortmöglichkeiten
     */
    public String[] getAnswers() {
        return answers;
    }
    
    /**
     * Prüft, ob die gegebene Antwort korrekt ist.
     * 
     * @param answerIndex Der Index der gegebenen Antwort (0-3)
     * @return wahr, wenn die Antwort korrekt ist, sonst falsch
     */
    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }
    
    /**
     * Gibt die korrekte Antwort zurück.
     * 
     * @return Die korrekte Antwort
     */
    public String getCorrectAnswer() {
        return answers[correctAnswerIndex];
    }
}
