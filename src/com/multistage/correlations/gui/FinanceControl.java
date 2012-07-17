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

public class FinanceControl extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JTree m_tree = null;

	protected DefaultTreeModel m_model = null;

	protected JTextField m_display;

	public FinanceControl() {

		Object[] nodes = new Object[4];

		/*
		 * DefaultMutableTreeNode top = new DefaultMutableTreeNode( new
		 * FinanceSelectorNode(1, "Analyse")); DefaultMutableTreeNode parent = top; nodes[0] =
		 * top;
		 */

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new FinanceSelectorNode(1,
				"Sorting algorithm"));
		DefaultMutableTreeNode clus_parent = node;
		DefaultMutableTreeNode parent = clus_parent;
		// parent.add(clus_parent);
		nodes[0] = clus_parent;

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(1, "K-means"));
		clus_parent.add(node);
		parent = node;
		nodes[1] = parent;

		/*
		 * node = new DefaultMutableTreeNode(new FinanceSelectorNode(1, "single pass"));
		 * parent.add(node); parent = node; nodes[3] = parent;
		 */

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(1,
				"random, fixed: single pass"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(2,
				"random, fixed: multipass"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(3,
				"exchange, best separation"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(4, "exchange, fixed"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(2, "HCL"));
		clus_parent.add(node);
		parent = node;
		nodes[2] = parent;

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(1, "fixed clusters"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(2, "best separation"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(3, "C-means fuzzy"));
		clus_parent.add(node);
		parent = node;
		nodes[3] = parent;

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(1, "fixed clusters"));
		parent.add(node);

		node = new DefaultMutableTreeNode(new FinanceSelectorNode(2, "best separation"));
		parent.add(node);

		/*
		 * DefaultMutableTreeNode node = new DefaultMutableTreeNode( new
		 * FinanceSelectorNode(0, "View")); parent.add(node); node = new
		 * DefaultMutableTreeNode(new FinanceSelectorNode(2, "Cluster")); parent.add(node);
		 * 
		 * node = new DefaultMutableTreeNode(new FinanceSelectorNode(3, "org"));
		 * parent.add(node); parent = node; nodes[1] = parent;
		 * 
		 * node = new DefaultMutableTreeNode(new FinanceSelectorNode(6, "dod"));
		 * parent.add(node); parent = node; nodes[2] = parent;
		 */

		/*
		 * node = new DefaultMutableTreeNode(new FinanceSelectorNode(1, "internet"));
		 * parent.add(node); parent = node; nodes[3] = parent;
		 * 
		 * node = new DefaultMutableTreeNode(new FinanceSelectorNode(1, "directory"));
		 * parent.add(node); node = new DefaultMutableTreeNode(new FinanceSelectorNode(2,
		 * "mgmt")); parent.add(node); nodes[4] = node; node.add(new
		 * DefaultMutableTreeNode(new FinanceSelectorNode(1, "mib-2"))); node = new
		 * DefaultMutableTreeNode(new FinanceSelectorNode(3, "experimental"));
		 * parent.add(node); node = new DefaultMutableTreeNode(new FinanceSelectorNode(4,
		 * "private")); node.add(new DefaultMutableTreeNode(new FinanceSelectorNode(1,
		 * "enterprises"))); parent.add(node); node = new
		 * DefaultMutableTreeNode(new FinanceSelectorNode(5, "security")); parent.add(node);
		 * node = new DefaultMutableTreeNode(new FinanceSelectorNode(6, "snmpV2"));
		 * parent.add(node); node = new DefaultMutableTreeNode(new FinanceSelectorNode(7,
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
		this.add(new Button(), BorderLayout.CENTER);
		// this.setPreferredSize(new Dimension(SetEnv.SizeB,100));
		s.setPreferredSize(new Dimension(SetEnv.SizeB,
				(int) (0.10 * SetEnv.SizeY)));
		this.setVisible(true);
	}

}

class FinanceSelectorNode {
	protected int m_id;

	protected String m_name;

	public FinanceSelectorNode(int id, String name) {
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
