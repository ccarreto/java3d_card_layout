package java3dwithcardlyout;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

// Simple demonstration of how to add java3D to a CardLayout setup
public class Java3dWithCardLyout extends Frame {

	public static void main(String[] args) {
		Frame frame = new Java3dWithCardLyout();
		frame.setPreferredSize(new Dimension(800, 800));
		frame.setTitle("Java 3D with CardLayout ");
		frame.pack();
		frame.setVisible(true);
	}

	public Java3dWithCardLyout() {
		
		// Create a panel with a CardLyout to hold the cards
		Panel cardPanel = new Panel();             
		CardLayout cardLayout = new CardLayout();  
		cardPanel.setLayout(cardLayout);            

		// There are 3 cards and each card contains a panel
		// Create card 1
		// Card 1 contains panel1 that is created by the My3DPanel that implements a 3D scene using Java3D
		Panel panel1 = new My3DPanel();             
		cardPanel.add(panel1, "1");
		
		// Create card 2
	    // Card 2 contains panel2 that shows a simple label
		// panel 2 can be customized like panel 1
		Panel panel2 = new Panel();
		Label label2 = new Label("Card 2");
		panel2.add(label2);
		cardPanel.add(panel2, "2");
		
		// Create card 3
		// Card 3 contains panel2 that shows a simple label
        // panel 3 can be customized like panel 1
		Panel panel3 = new Panel();
		Label label3 = new Label("Card 3");
		panel3.add(label3);
		cardPanel.add(panel3, "3");
		
		// Create a panel to hold 3 buttons to select the card to show 
		Panel buttonPanel = new Panel();
		
		// Create and add button 1
		Button button1 = new Button("Button 1");
		buttonPanel.add(button1);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cardLayout.show(cardPanel, "1");
			}
		});
		
		// Create and add button 2
		Button button2 = new Button("Button 2");
		buttonPanel.add(button2);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cardLayout.show(cardPanel, "2");
			}
		});
		
		// Create and add button 3
		Button button3 = new Button("Button 3");
		buttonPanel.add(button3);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cardLayout.show(cardPanel, "3");
			}
		});

		// Add the cardPanel and the buttonPanel to the frame
		setLayout(new BorderLayout()); 
		add(cardPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
		
		// Add a listener to end the program when the window is closed
		WindowListener wListener = new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		};
		this.addWindowListener(wListener);
	}
}

class My3DPanel extends Panel {
	
	public My3DPanel() {
		// Set the the panel size 
		setPreferredSize(new Dimension(800, 695));
        
		// Create a Canvas3d object to render 3D graphics
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv1 = new Canvas3D(gc);

		// Add the canvas to the panel
		setLayout(new BorderLayout()); 
		add(cv1, BorderLayout.CENTER);

		// Create the a simple universe with a standard nominal view
		SimpleUniverse su = new SimpleUniverse(cv1); // 
		su.getViewingPlatform().setNominalViewingTransform();

		// Create and add a content branch to the simple universe
		BranchGroup bg = createSceneGraph();
		bg.compile();
		su.addBranchGraph(bg); 

		// Add a OrbitBehavior to control the view with the mouse
		OrbitBehavior orbit = new OrbitBehavior(cv1);
		orbit.setSchedulingBounds(new BoundingSphere());
		su.getViewingPlatform().setViewPlatformBehavior(orbit);
	}

	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();

        // Simple content branch formed by a ColorCube object that is a child of a TransformGroup.
		ColorCube cc = new ColorCube(0.4);
		Transform3D tr = new Transform3D();
		tr.setScale(0.5);
		tr.setRotation(new AxisAngle4d(1, 1, 1, Math.PI / 4));
		TransformGroup tg = new TransformGroup(tr);
		root.addChild(tg);
		tg.addChild(cc);
		
		return root;
	}
}
