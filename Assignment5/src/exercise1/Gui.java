package exercise1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.SplittableRandom;

public class Gui implements ActionListener, PropertyChangeListener {

	private JFrame frame;
	private JProgressBar progressBar;
	private JButton startButton;
	private JButton cancelButton;
	private JLabel label;
	private CalculatePiTask task;

	class CalculatePiTask extends SwingWorker<Void, Void> {
		double experiment = 0;
		
		public int getAmountOfCorrectFractions(double experiment, double control) {
			if((int)experiment != (int)control) {
				return 0;
			}
			int count = 0;
			while((int)experiment == (int)control) {
				experiment -= (int)experiment;
				experiment *= 10;
				control -= (int)control;
				control *= 10;
				count++;
			}
			return count-1; // we only care about the amount of correct fractions
		}
		
		@Override
		public Void doInBackground() {
			setProgress(0);
			int currentFractions = 0;
			int desiredFractions = 8;
			double n_circle = 0;
			double n_square = 0;
			SplittableRandom r = new SplittableRandom();
			while((currentFractions < desiredFractions) && !isCancelled()) {
				double x = r.nextDouble();
				double y = r.nextDouble();
				n_square++;
				if(x*x + y*y <= 1) {
					n_circle++;
				}
				experiment = (n_circle/n_square)*4.0;
				currentFractions = getAmountOfCorrectFractions(experiment, Math.PI);
				int progress = (int)(((float)currentFractions/desiredFractions)*100);
				setProgress(Math.min(Math.max(getProgress(), progress), 100));
				label.setText("Experimental PI: "+experiment);
			}
			if(!isCancelled()) setProgress(100);
			return null;
		}

		@Override
		public void done() {
			cancelButton.setEnabled(false);
			label.setText("Experimental PI: "+experiment);
			label.setForeground(Color.getHSBColor(139.0f/255.0f, 100.0f/255.0f, 60.0f/255.0f));
			if(isCancelled()) label.setForeground(Color.red);
			startButton.setEnabled(true);
		}
	}

	public Gui() {
		frame = new JFrame("MonteCarloCalculatePi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startButton = new JButton("start");
		startButton.addActionListener(this);
		cancelButton = new JButton("cancel");
		cancelButton.addActionListener(this);
		progressBar = new JProgressBar(0,100);
		label = new JLabel("Experimental PI: ");
		cancelButton.setEnabled(false);
		frame.getContentPane().add(startButton, BorderLayout.WEST);
		frame.getContentPane().add(progressBar, BorderLayout.CENTER);
		frame.getContentPane().add(cancelButton, BorderLayout.EAST);
		frame.getContentPane().add(label, BorderLayout.SOUTH);
    }

	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource().equals(startButton)) {
			startButton.setEnabled(false);
			cancelButton.setEnabled(true);
			label.setText("Experimental PI: ");
			label.setForeground(Color.black);
			task = new CalculatePiTask();
			task.addPropertyChangeListener(this);
			task.execute();
		} else {
			cancelButton.setEnabled(false);
			startButton.setEnabled(true);
			task.cancel(true);
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		}
	}

	private static void createAndShowGUI() {
		Gui gui = new Gui();
		gui.frame.pack();
		gui.frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
