package com.multistage.correlations.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Cluster selector
 * 
 * @author H.Geissler 18 May 2011
 * 
 */

public class FSelector extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JTree m_tree = null;

	protected DefaultTreeModel m_model = null;

	protected JTextField m_display;

	public FSelector() {

		Object[] nodes = new Object[4];

		/*
		 * DefaultMutableTreeNode top = new DefaultMutableTreeNode( new
		 * FDatNode(1, "Analyse")); DefaultMutableTreeNode parent = top; nodes[0] =
		 * top;
		 */

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new FDatNode(1,
				"X"));
		DefaultMutableTreeNode clus_parent = node;
		DefaultMutableTreeNode parent = clus_parent;
		// parent.add(clus_parent);
		nodes[0] = clus_parent;
		node = new DefaultMutableTreeNode(new FDatNode(1000, "Datax"));
		clus_parent.add(node);
		/*
		 * node = new DefaultMutableTreeNode(new FDatNode(1, "internet"));
		 * parent.add(node); parent = node; nodes[3] = parent;
		 * 
		 * node = new DefaultMutableTreeNode(new FDatNode(1, "directory"));
		 * parent.add(node); node = new DefaultMutableTreeNode(new FDatNode(2,
		 * "mgmt")); parent.add(node); nodes[4] = node; node.add(new
		 * DefaultMutableTreeNode(new FDatNode(1, "mib-2"))); node = new
		 * DefaultMutableTreeNode(new FDatNode(3, "experimental"));
		 * parent.add(node); node = new DefaultMutableTreeNode(new FDatNode(4,
		 * "private")); node.add(new DefaultMutableTreeNode(new FDatNode(1,
		 * "enterprises"))); parent.add(node); node = new
		 * DefaultMutableTreeNode(new FDatNode(5, "security")); parent.add(node);
		 * node = new DefaultMutableTreeNode(new FDatNode(6, "snmpV2"));
		 * parent.add(node); node = new DefaultMutableTreeNode(new FDatNode(7,
		 * "mail"));
		 */
		// parent.add(node);
		m_model = new DefaultTreeModel(clus_parent);
		m_tree = new JTree(m_model);
		// m_tree.setPreferredSize(new Dimension(SetEnv.SizeB,150));

		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setOpenIcon(new ImageIcon("opened.gif"));
		renderer.setClosedIcon(new ImageIcon("closed.gif"));
		renderer.setLeafIcon(new ImageIcon("leaf.gif"));
		m_tree.setCellRenderer(renderer);

		m_tree.setShowsRootHandles(true);
		m_tree.setEditable(false);
		TreePath path = new TreePath(nodes);
		m_tree.setSelectionPath(path);

		JScrollPane s = new JScrollPane();
		s.getViewport().add(m_tree);
		this.add(s, BorderLayout.CENTER);
		// this.setPreferredSize(new Dimension(SetEnv.SizeB,100));
		s.setPreferredSize(new Dimension(SetEnv.SizeB,
				(int) (0.12 * SetEnv.SizeY)));
		this.setVisible(true);
	}

}

class FDatNode {
	protected int m_id;

	protected String m_name;

	public FDatNode(int id, String name) {
		m_id = id;
		m_name = name;
	}

	public int getId() {
		return m_id;
	}

	public String getName() {
		return m_name;
	}

	public String toString() {
		return m_name;
	}
}
