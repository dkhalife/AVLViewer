package com.dkhalife.projects;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * AVL Viewer is an application that calculates and draws a representation of an
 * AVL Tree
 * 
 * @author Dany Khalife
 */
public class AvlViewer {
	// We need an input field
	private static JFormattedTextField tfInput;

	// 3 Buttons
	private static JButton btnAdd;
	private static JButton btnDelete;
	private static JButton btnClear;

	// And a panel
	private static Panel p;

	/**
	 * Once an AVL Viewer is created, it will create and show the GUI
	 */
	public AvlViewer() {
		createAndShowGUI();
	}

	/**
	 * Main method, entry point for this application
	 * 
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AvlViewer();
			}
		});
	}

	/**
	 * This methode creates and shows the GUI
	 */
	private static void createAndShowGUI() {
		// Create a window
		JFrame window = new JFrame("AVL Viewer");

		// Make it visible and not resizable
		window.setVisible(true);
		window.setResizable(false);

		// The close operation
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Create and add a panel
		JPanel panel = new JPanel(new BorderLayout());
		window.add(panel);

		// Then create the layout for the buttons
		Box vb = Box.createVerticalBox();
		panel.add(vb);

		Box hb = Box.createHorizontalBox();
		vb.add(hb);

		// The title
		JLabel title = new JLabel("AVL Viewer");
		title.setFont(new Font("Verdana", 1, 20));

		// Add margins to it
		setMargin(title, 20, 0, 30, 0);

		// Center H
		hb.add(Box.createHorizontalGlue());
		hb.add(title);
		hb.add(Box.createHorizontalGlue());

		hb = Box.createHorizontalBox();
		vb.add(hb);

		hb.add(Box.createHorizontalStrut(360));

		// Create the input field
		tfInput = new JFormattedTextField(NumberFormat.getInstance());
		tfInput.setDocument(new JTextFieldLimit(2));
		hb.add(tfInput);

		hb.add(Box.createHorizontalStrut(10));

		// The add button
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AvlViewer.addVertex();
			}
		});
		hb.add(btnAdd);

		hb.add(Box.createHorizontalStrut(10));

		// The delete button
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AvlViewer.deleteVertex();
			}
		});
		hb.add(btnDelete);

		hb.add(Box.createHorizontalStrut(50));

		// And the clear button
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AvlViewer.clear();
			}
		});
		hb.add(btnClear);

		hb.add(Box.createHorizontalStrut(360));

		vb.add(Box.createVerticalStrut(10));

		// Then add our panel
		p = new Panel(50, 20);

		hb = Box.createHorizontalBox();
		vb.add(hb);

		hb.add(Box.createHorizontalStrut(10));
		hb.add(p);
		hb.add(Box.createHorizontalStrut(10));

		vb.add(Box.createVerticalStrut(10));

		// Pack the whole thing to make sure everything is propperly aligned
		window.pack();
	}

	/**
	 * This method sets the margins for a component
	 * @param c The conponent
	 * @param top The top margin
	 * @param right The right margin
	 * @param bottom The bottom margin
	 * @param left The left margin
	 */
	private static void setMargin(JComponent c, int top, int right, int bottom, int left) {
		Border current = c.getBorder();

		Border margin = new EmptyBorder(top, left, bottom, right);

		if (current == null) {
			c.setBorder(margin);
		}
		else {
			c.setBorder(new CompoundBorder(margin, current));
		}
	}

	/**
	 * This method attemps to add an element to the tree
	 */
	public static void addVertex() {
		try {
			p.addVertex(Integer.parseInt(tfInput.getText()));
			tfInput.setText("");
		} catch (NumberFormatException localNumberFormatException) {
		}
	}

	/**
	 * This method attemps to delete an element from the tree
	 */
	public static void deleteVertex() {
		try {
			p.deleteVertex(Integer.parseInt(tfInput.getText()));
			tfInput.setText("");
		} catch (NumberFormatException localNumberFormatException) {
		}
	}

	/**
	 * This method clears the tree
	 */
	public static void clear() {
		p.clear();
	}
}