/**
 * <h2>Viewing Window</h2>
 * JPanel for loading and viewing business card graphics.
 * <p>
 * <b>Note: </b>The save files have a few quirks to be aware of:
 * 	-They do not support nested tags other than within {@code <logo>}
 * 		(i.e. A {@code <rect>} tag within another {@code <rect>} tag)
 * 	-This program will not draw anything with ill formatted data within tags
 * 		(i.e. A {@code String} within a {@code <width>} tag instead of
 * 		an {@code int})
 * <p>
 * <b>Example use:</b>
 * {@code
 * JFrame mainWindow = new JFrame();
 * maineWindow.setComponentPane(new ViewWindow());}
 *
 * Tutorials that assisted along the way with XML and GUI interaction
 * @see <a href="https://docs.oracle.com/javase/tutorial/jaxp/dom/
 * readingXML.html">here</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/
 * components/button.html">here</a>
 *
 * @author Silas Agnew
 * @version 1.0.0
 */

package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewWindow extends JPanel
                        implements ActionListener
{
	// For easier parsing of rgba colors
	private static final char[] RGBA = "rgba".toCharArray();
	private static final Color SHADOW_COLOR = new Color(0, 0, 0, 120);
	private static final int SHADOW_OFFSET = 10;
	
	private JButton loadButton;
	private JButton clearButton;
	
	private JFileChooser fileChooser;
	private File xmlFile;
	
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document document;
	
	private int cardWidth = 0;
	private int cardHeight = 0;
	private int cardX = 0;
	private int cardY = 0;
	
	private boolean clearScreen = false;
	
    /**
     * Loads all GUI components and XML parser
     */
	public ViewWindow()
	{
		// button(s)
		loadButton = new JButton("Load");
		loadButton.setActionCommand("load");
		loadButton.setToolTipText(
				"Choose a new card save file to load and view.");
		loadButton.addActionListener(this);
		
		clearButton = new JButton("Clear");
		clearButton.setActionCommand("clear");
		clearButton.setToolTipText("Clears the screen");
		clearButton.addActionListener(this);
		
		// file chooser
		fileChooser = new JFileChooser();
		
		// Secretly XML, shh..
		fileChooser.setFileFilter(
				new FileNameExtensionFilter(
				"Card Save Files", "card"));
		
		// Sets to current dir of program
		fileChooser.setCurrentDirectory(new File("."));
		
		// Xml
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setIgnoringComments(true);
		docBuilderFactory.setIgnoringElementContentWhitespace(false);
		try
		{
			docBuilder = docBuilderFactory.newDocumentBuilder();
		}
		catch (ParserConfigurationException ex)
		{
			JOptionPane.showMessageDialog(this,
					"DocumentBuilderFactory Error: " + ex.getMessage(),
					"Warning", JOptionPane.WARNING_MESSAGE, null);
		}
		loadFile();
	}
	
	/**
	 * Parses an xml document from a selected file
	 * @return true if document was successfully parsed
	 */
	private boolean parseXml()
	{
		try
		{
			document = docBuilder.parse(xmlFile);
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(
					this, "File I/O Error: " + ex.getMessage(),
					"Warning", JOptionPane.WARNING_MESSAGE, null);
			return false;
		}
		catch (SAXException ex)
		{
			JOptionPane.showMessageDialog(
					this, "SAX Xml Error: " + ex.getMessage(),
					"Warning", JOptionPane.WARNING_MESSAGE, null);
			return false;
		}
		return true;
	}
	
	/**
	 * Finds the child node of {@code node} with name that matches
	 * {@code tag}
	 * @param node Node to search for children of
	 * @param tag Child node name
	 * @return Found child node
	 */
	private Node findChild(Node node, String tag) {
		Node n = node.getFirstChild();
		while (true)
		{
			if (n.getNextSibling() == null) return null;
			if (n.getNodeName().equals(tag))
				return n;
			n = n.getNextSibling();
		}
	}

	/**
	 * Called whenever a new save file is chosen
	 * @return If a file load was successful
	 */
	private boolean loadFile()
	{
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			xmlFile = fileChooser.getSelectedFile();
		}
		else return false;
		parseXml();
		repaint();
		return true;
	}
	
	//-Interpreting Methods----------------------------------------//
	
	/**
	 * Finds the child color node within {@code node} and interprets RGBa
	 * values
	 * @param node Node to find the color of
	 * @return The color interpreted
	 */
	private Color findColor(Node node)
	{
		int[] rgba = { 0, 0, 0, 0 };
		Node colorNode = findChild(node, "color");
		
		if (colorNode == null)
			return null;
		
		for (int i = 0; i < rgba.length; i++)
		{
			Node n = findChild(colorNode, String.valueOf(RGBA[i]));
			String s = n.getTextContent();
			rgba[i] = Integer.parseInt(s);
		}
		return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
	}
	
	/**
	 * Finds and interprets the dimension tags ({@code <x>, <y>, <width>,}
	 * and {@code <height>}) of parent nodes. Includes protection from
	 * exceptions like {@code <line>} only having 2
	 * coordinates rather than 1 coordinate and size
	 * @param node Node to find dimension tags as children of
	 * @return Array (size = 4) of 2 points. If a value = -1 it is invalid
	 * 	or unused.
	 */
	private int[] findDimensions(Node node)
	{
		int[] values = {-1, -1, -1, -1};
		
		try
		{
			// No Height or Width
			if (node.getNodeName().equals("text"))
			{
				values[0] = Integer.parseInt(
						findChild(node, "x").getTextContent());
				values[1] = Integer.parseInt(
						findChild(node, "y").getTextContent());
			}
			// 2 XYs
			else if (node.getNodeName().equals("line"))
			{
				values[0] = Integer.parseInt(
						findChild(node, "x").getTextContent());
				values[1] = Integer.parseInt(
						findChild(node, "y").getTextContent());
				values[2] = Integer.parseInt(
						findChild(node, "x2").getTextContent());
				values[3] = Integer.parseInt(
						findChild(node, "y2").getTextContent());
			}
			else if (node.getNodeName().equals("pic"))
			{
				values[0] = Integer.parseInt(
						findChild(node, "x").getTextContent());
				values[1] = Integer.parseInt(
						findChild(node, "y").getTextContent());
			}
			else
			{
				values[0] = Integer.parseInt(
						findChild(node, "x").getTextContent());
				values[1] = Integer.parseInt(
						findChild(node, "y").getTextContent());
				values[2] = Integer.parseInt(
						findChild(node, "width").getTextContent());
				values[3] = Integer.parseInt(
						findChild(node, "height").getTextContent());
			}
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(
					this, "XML File format error: " + ex.getMessage(),
					"Warning", JOptionPane.WARNING_MESSAGE, null);
		}
		return values;
	}
	
	/**
	 * Used only on {@code <text>} tags to get the string with which to
	 * display
	 * @param node The {@code <text>} node
	 * @return The content to display
	 */
	private String findContent(Node node)
	{
		// maybe will work
		if (!node.getNodeName().equals("text")) return "";
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
		{
			if (list.item(i).getNodeName().equals("content"))
				return list.item(i).getTextContent();
		}
		return null;
	}

	/**
	 * Finds the font, style, and size of the {@code <text>} tag
	 * @param node {@code <text>} tag node
	 * @return The configured font
	 */
	private Font findFont(Node node)
	{
		int size = 0;
		String styleStr = "";
		int style = 0;

		Node n = findChild(node, "font");
		size = Integer.parseInt(
				n.getAttributes().getNamedItem("size").getNodeValue());
		styleStr = n.getAttributes().getNamedItem("style").getNodeValue();

		switch (styleStr)
		{
			case "italics":
				style = Font.ITALIC;
				break;
			case "bold":
				style = Font.BOLD;
				break;
			case "plain":
				style = Font.PLAIN;
				break;
			default:
				style = Font.PLAIN;
				break;
		}

		return new Font(n.getTextContent(), style, size);
	}

	/**
	 * Gets the Location of image from save file and loads that image to a
	 * buffer for use
	 * @param node {@code <pic>} node to search for resource location in
	 * @return Buffered Image for use
	 */
	private BufferedImage findImage(Node node)
	{
		BufferedImage photo = null;
		Node n = findChild(node, "path");
		try
		{
			File file = new File(n.getTextContent());
			photo = ImageIO.read(file);
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(
					this, "Image Error: " + ex.getMessage(),
					"Warning", JOptionPane.WARNING_MESSAGE, null);
		}
		return photo;
	}
	
	/**
	 * Paints all children objects of {@code node} to the screen
	 * @param g Graphics object used to paint
	 * @param node XML Node to paint
	 */
	public void paintNode(Graphics g, Node node)
	{
		String xStr = node.getAttributes().getNamedItem("x").getNodeValue();
		String yStr = node.getAttributes().getNamedItem("y").getNodeValue();
		
		// Determines offset of node relative to its parent node
		// This only works for nodes that are children of the root node
		int x = (!xStr.equals("")) ? Integer.parseInt(xStr) : 0;
		int y = (!yStr.equals("")) ? Integer.parseInt(yStr) : 0;
		
		NodeList nList = node.getChildNodes();
		
		for (int i = 0; i < nList.getLength(); i++)
		{
			Node n = nList.item(i);

			int[] values;
			switch (n.getNodeName())
			{
				case "rect":
					g.setColor(findColor(n));
					values = findDimensions(n);
					g.fillRect(values[0] + x + cardX, values[1] + y + cardY,
							values[2], values[3]);
					break;
				case "oval":
					g.setColor(findColor(n));
					values = findDimensions(n);
					g.fillOval(values[0] + x + cardX, values[1] + y + cardY,
							values[2], values[3]);
					break;
				case "line":
					g.setColor(findColor(n));
					values = findDimensions(n);
					g.drawLine(values[0] + x + cardX, values[1] + y + cardY,
							values[2] + x + cardX, values[3] + y + cardY);
					break;
				case "text":
					g.setColor(findColor(n));
					g.setFont(findFont(n));
					values = findDimensions(n);
					g.drawString(findContent(n),
							values[0] + x + cardX, values[1] + y + cardY);
					break;
				case "pic":
					g.setColor(findColor(n));
					values = findDimensions(n);
					g.drawImage(findImage(n), values[0] + cardX,
							values[1] + cardY, null);
					break;
				default:
					continue;
			}
		}
	}
	
	/**
	 * Called to draw all objects to the screen
	 * @see {@link javax.swing.JComponent#paintComponent(Graphics)}
	 * @param g Graphics object used to draw to the screen
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(Color.CYAN);
		
		loadButton.setLocation(5, 5);
		add(loadButton);
		
		clearButton.setLocation(
				loadButton.getX() + loadButton.getWidth() + 5, 5);
		add(clearButton);
		
		// Check if this call is to clear screen or draw a card
		if (clearScreen)
		{
			clearScreen = false;
			return;
		}
		
		// Card body & shadow
		Node card = document.getFirstChild();
		
		// Position of card
		cardWidth = Integer.parseInt(
				card.getAttributes().getNamedItem("width").getNodeValue());
		cardHeight = Integer.parseInt(
				card.getAttributes().getNamedItem("height").getNodeValue());
		
		cardX = (getWidth() / 2) - (cardWidth / 2);
		cardY = (getHeight() / 2) - (cardHeight / 2);
		
		// Shadow
		g.setColor(SHADOW_COLOR);
		g.fillRect(cardX + SHADOW_OFFSET, cardY + SHADOW_OFFSET,
				cardWidth, cardHeight);
		
		// Card
		g.setColor(findColor(card));
		g.fillRect(cardX, cardY, cardWidth, cardHeight);
		
		// Card body content
		paintNode(g, card);
		
		// Logo
		paintNode(g, findChild(card, "logo"));
	}
	
	/**
	 * Called when a button is pressed within this object
	 * @see {@link java.awt.event.ActionListener#actionPerformed(ActionEvent)}
	 * @param actionEvent Event with information on button press
	 */
	@Override
    public void actionPerformed(ActionEvent actionEvent)
	{
    	// Prompt for loading a card save
    	if ("load".equals(actionEvent.getActionCommand()))
		{
			loadFile();
		}
		
		// Clears the screen of cards
		else if ("clear".equals(actionEvent.getActionCommand()))
		{
			clearScreen = true;
			removeAll();
			repaint();
		}
    }
}
