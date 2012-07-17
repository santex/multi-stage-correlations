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

public class CSelector extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JTree m_tree = null;

	protected DefaultTreeModel m_model = null;

	protected JTextField m_display;

	public CSelector() {

		Object[] nodes = new Object[4];

		/*
		 * DefaultMutableTreeNode top = new DefaultMutableTreeNode( new
		 * OidNode(1, "Analyse")); DefaultMutableTreeNode parent = top; nodes[0] =
		 * top;
		 */

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new OidNode(1,
				"Sorting algorithm & Data"));
		DefaultMutableTreeNode clus_parent = node;
		DefaultMutableTreeNode parent = clus_parent;
		// parent.add(clus_parent);
		nodes[0] = clus_parent;
		node = new DefaultMutableTreeNode(new OidNode(1000, "Data"));
		clus_parent.add(node);
		
		nodes[2] = node;
		node = new DefaultMutableTreeNode(new OidNode(1, "K-means"));
		clus_parent.add(node);
		parent = node;
		nodes[1] = parent;

		/*
		 * node = new DefaultMutableTreeNode(new OidNode(1, "single pass"));
		 * parent.add(node); parent = node; nodes[3] = parent;
		 */

		node = new DefaultMutableTreeNode(new OidNode(1,
				"random, fixed: single pass"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new OidNode(2,
				"random, fixed: multipass"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new OidNode(3,
				"exchange, best separation"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new OidNode(4, "exchange, fixed"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new OidNode(2, "HCL"));
		clus_parent.add(node);
		parent = node;
		nodes[2] = parent;

		node = new DefaultMutableTreeNode(new OidNode(1, "fixed clusters"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new OidNode(2, "best separation"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new OidNode(3, "C-means fuzzy"));
		clus_parent.add(node);
		parent = node;
		nodes[3] = parent;

		node = new DefaultMutableTreeNode(new OidNode(1, "fixed clusters"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new OidNode(2, "best separation"));
		parent.add(node);

		/*
		 * DefaultMutableTreeNode node = new DefaultMutableTreeNode( new
		 * OidNode(0, "View")); parent.add(node); node = new
		 * DefaultMutableTreeNode(new OidNode(2, "Cluster")); parent.add(node);
		 * 
		 * node = new DefaultMutableTreeNode(new OidNode(3, "org"));
		 * parent.add(node); parent = node; nodes[1] = parent;
		 * 
		 * node = new DefaultMutableTreeNode(new OidNode(6, "dod"));
		 * parent.add(node); parent = node; nodes[2] = parent;
		 */

		/*
		 * node = new DefaultMutableTreeNode(new OidNode(1, "internet"));
		 * parent.add(node); parent = node; nodes[3] = parent;
		 * 
		 * node = new DefaultMutableTreeNode(new OidNode(1, "directory"));
		 * parent.add(node); node = new DefaultMutableTreeNode(new OidNode(2,
		 * "mgmt")); parent.add(node); nodes[4] = node; node.add(new
		 * DefaultMutableTreeNode(new OidNode(1, "mib-2"))); node = new
		 * DefaultMutableTreeNode(new OidNode(3, "experimental"));
		 * parent.add(node); node = new DefaultMutableTreeNode(new OidNode(4,
		 * "private")); node.add(new DefaultMutableTreeNode(new OidNode(1,
		 * "enterprises"))); parent.add(node); node = new
		 * DefaultMutableTreeNode(new OidNode(5, "security")); parent.add(node);
		 * node = new DefaultMutableTreeNode(new OidNode(6, "snmpV2"));
		 * parent.add(node); node = new DefaultMutableTreeNode(new OidNode(7,
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
				(int) (0.27 * SetEnv.SizeY)));
		this.setVisible(true);
	}

}

class OidNode {
	protected int m_id;

	protected String m_name;

	public OidNode(int id, String name) {
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
