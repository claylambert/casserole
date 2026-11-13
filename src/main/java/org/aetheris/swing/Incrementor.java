/*
 * Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
 * All rights reserved.
 *
 * Author: Clay Lambert
 *
 * Licensed under the MIT License.
 * If you use or modify this source, you must include this copyright notice
 * and the MIT License permission notice in all copies or substantial portions
 * of the software, including modifications.
 */

package org.aetheris.swing;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The {@code Incrementor} class is a row-count control with minus, a text field, and plus.
 * A typed value waits 800 milliseconds, and then the listener hears the count.
 * Plus and minus apply at once.
 * The count stays at least 1.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#Incrementor
 */
// @aa-sdd PK-731 docs/specs/common/stages.spec.md
// @aa-sdd GK-1273 aetheris-plan-stage-1-tweaks.003.md
// @aa-sdd PK-32 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd GK-1290 aetheris-plan-stage-1-tweaks.005.md
// @aa-sdd PK-38 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-757 docs/specs/common/stages.spec.md
// @aa-sdd PK-51 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
// @aa-sdd PK-84 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
// @aa-sdd PK-111 docs/specs/java/org/aetheris/swing/swing.spec.md
public final class Incrementor extends JPanel {

    // @aa-sdd L40 aa-sdd-source-prose.md#Incrementor
    /**
     * The {@code Listener} interface receives a row count after it changes.
     */
    public interface Listener {
        /**
         * The method receives the row count after a real change.
         *
         * @param rows The rows value is the applied count, and it is at least 1.
         */
        void rowsChanged(int rows);
    }

    private final JButton minusButton;
    private final JButton plusButton;
    private final JTextField field;
    private final Listener listener;
    private final Timer timer;
    private int rows;
    private boolean suppress;

    /**
     * The constructor builds the minus button, the field, and the plus button.
     * A starting count below 1 is stored as 1.
     * The typed-value timer waits 800 milliseconds and does not repeat.
     *
     * @param rows The rows value is the starting count.
     * @param listener The listener is notified after a real change, and it may be null.
     */
    public Incrementor(int rows, Listener listener) {
        super(new FlowLayout(FlowLayout.LEFT, 4, 0));
        this.listener = listener;
        this.rows = rows < 1 ? 1 : rows;
        minusButton = new JButton("-");
        plusButton = new JButton("+");
        field = new JTextField(Integer.toString(this.rows), 4);
        /**
         * The timer listener applies the typed count when the wait ends.
         */
        timer = new Timer(800, new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#Incrementor
            @Override
            public void actionPerformed(ActionEvent e) {
                applyTyped();
            }
        });
        timer.setRepeats(false);
        /**
         * The minus listener subtracts one from the current count at once.
         */
        minusButton.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#Incrementor
            @Override
            public void actionPerformed(ActionEvent e) {
                applyNow(Incrementor.this.rows - 1);
            }
        });
        /**
         * The plus listener adds one to the current count at once.
         */
        plusButton.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#Incrementor
            @Override
            public void actionPerformed(ActionEvent e) {
                applyNow(Incrementor.this.rows + 1);
            }
        });
        /**
         * The document listener restarts the 800 millisecond wait after each edit.
         */
        field.getDocument().addDocumentListener(new DocumentListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#Incrementor
            @Override
            public void insertUpdate(DocumentEvent e) {
                schedule();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                schedule();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                schedule();
            }
        });
        add(minusButton);
        add(field);
        add(plusButton);
    }

    /**
     * The method returns the minus button.
     *
     * @return The return value is the button labeled {@code -}.
     */
    public JButton getMinusButton() {
        return minusButton;
    }

    /**
     * The method returns the plus button.
     *
     * @return The return value is the button labeled {@code +}.
     */
    public JButton getPlusButton() {
        return plusButton;
    }

    /**
     * The method returns the editable count field.
     *
     * @return The return value is that text field.
     */
    public JTextField getField() {
        return field;
    }

    /**
     * The method restarts the 800 millisecond timer.
     * A field update that this class itself wrote does not restart the timer.
     */
    private void schedule() {
        if (suppress) {
            return;
        }
        timer.restart();
    }

    /**
     * The method stores a count at once and stops the timer.
     * A count below 1 is stored as 1.
     * A count equal to the current count only refreshes the field.
     * A different count updates the field and notifies the listener when one is present.
     *
     * @param next The next value is the count to apply.
     */
    private void applyNow(int next) {
        timer.stop();
        int value = next < 1 ? 1 : next;
        if (value == rows) {
            showRows();
            return;
        }
        rows = value;
        showRows();
        if (listener != null) {
            listener.rowsChanged(rows);
        }
    }

    /**
     * The method applies the text in the field.
     * A null or blank field restores the current count and does not notify.
     * A value that is not an integer restores the current count and does not notify.
     * An integer is passed to {@link #applyNow}.
     */
    private void applyTyped() {
        String text = field.getText();
        if (text == null) {
            text = "";
        }
        text = text.trim();
        if (text.length() == 0) {
            showRows();
            return;
        }
        try {
            applyNow(Integer.parseInt(text));
        } catch (NumberFormatException ex) {
            showRows();
        }
    }

    /**
     * The method writes the current count into the field.
     * A field that already shows that count is left alone.
     * The write does not restart the timer.
     */
    private void showRows() {
        String text = Integer.toString(rows);
        if (text.equals(field.getText())) {
            return;
        }
        suppress = true;
        field.setText(text);
        suppress = false;
    }
}
