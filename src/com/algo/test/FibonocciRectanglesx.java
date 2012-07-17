package com.algo.test;

import harmonic.mz.function.*;
import harmonic.cad.shapes.*;
import harmonic.cad.manager.*;
import harmonic.mz.manager.*;
import harmonic.mz.gui.*;
import harmonic.mz.data.fields.*;
import harmonic.mz.data.*;
import harmonic.viewer3D.*;
import harmonic.meshGeom.math.*;
import java.util.*;
import java.awt.*;

public class FibonocciRectanglesx extends Module{
	IntField count = new IntField("count", 5);

	public FibonocciRectanglesx(){
		setName("Fibonocci Rectangles");
		MZ.modules().loadModule("harmonic.cad.manager.CAD");
	}

	public Component macroGUI(){
		Vector nodes = new Vector();
		nodes.add(count);
		return new NodeValuesDialog(nodes, "");
	}

	public void execute(){
		fib(count.getInt());
	}

	public void fib(int n){
		float f0 = 0;
		float f1 = 1;
		float f2 = 1;
		Pnt3f a = new Pnt3f();
		Pnt3f b = new Pnt3f(-1.f, -1.f, 0.f);
		Pnt3f a0 = new Pnt3f();
		Clr3f clr = new Clr3f(1.f, 1.f, 0.f);
		ShapeNode group = new ShapeNode("FibSquares");
		group.addToCurrent();

		//handy cad-zilla helpers
		CAD_HighLevels cad = new CAD_HighLevels();

		ShapeNode def = null;
		/* first is empty
		def = cad.rect(a, b);
		def = cad.region(def);
		group.addNode(def);
		((RegionShape)def).fillColor.setAllColors(clr);
		*/

		//figure max scale
		for (int i = 1; i < n; i++){
			f2 = f0 + f1;
			f0 = f1;
			f1 = f2;
		}

		//pre scale viewpoint to seem impressive
		TMat mat = new TMat();
		mat.scale(1.f/f2);
		mat.translate(-0.4f, -0.3f, 0.f);
		Viewport viewport = ViewerCore.viewports().getActiveView();

		//		viewport.setViewPerGlobal(mat);
		f0 = 0;
		f1 = 1;
		for (int i = 1; i < n; i++){
			f2 = f0 + f1;
			switch (i%4){
			case 0:{
				a.x = a0.x + f1 + f2;
				a.y = a0.y;
				b.x = a.x - f2;
				b.y = a.y - f2;
				clr = new Clr3f(0.f, 0.75f, 0.f);
				break ;
			}
			case 1:{
				a.x = a0.x;
				a.y = a0.y - f1 - f2;
				b.x = a.x - f2;
				b.y = a.y + f2;
				clr = new Clr3f(0.5f, 0.5f, 0.f);
				break;
			}
			case 2:{
				a.x = a0.x - f1 - f2;
				a.y = a0.y;
				b.x = a.x + f2;
				b.y = a.y + f2;
				clr = new Clr3f(0.65f, 0.f, 0.f);
				break;
			}
			case 3:{
				a.x = a0.x;
				a.y = a0.y + f1 + f2;
				b.x = a.x + f2;
				b.y = a.y - f2;
				clr = new Clr3f(0.f, 0.f, 0.65f);
				break;
			}
			}

			Pnt3f center = new Pnt3f((a.x + b.x)/2.f, (a.y + b.y)/2.f, 0.f);
			//shrink a little
			float shrink = 0.1f;
			Rect4f r = new Rect4f(a, b);
			r.ax += shrink;
			r.ay += shrink;
			r.bx -= shrink;
			r.by -= shrink;
			def = cad.rect(new Pnt3f(r.ax, r.ay, 0.f), new Pnt3f(r.bx, r.by, 0.f));
			/*RegionShape region = ((RegionShape)def)region(def);
			region.fillColor.setAllColors(clr);
			region.fillColor.makeUnreferenced();
			region.lineColor.setAllColors(new Clr3f(1.f, 1.f, 1.f));
			region.lineColor.makeUnreferenced();
			region.surface.xSurfaceMode.setInt(2);
			region.surface.makeUnreferenced();
			*/
			group.addNode(group);
			viewport.renderNow();
			f0 = f1;
			f1 = f2;
			a0.set(a);
		}

		//spin viewpoint, of course
		if(false){
			float oldScale = mat.calcScale();
			float newScale = oldScale/10.f;
			for (int j = 0; j < 20; j++){
				mat.setIdentity();
				int q = (int)(Math.random()*3.);
				System.out.println("q: " + q);
				switch (q){
				case 0:{
					mat.fromXRotation(5.f*Geo.radPerDeg);
					break;
				}
				case 1:{
					mat.fromYRotation(5.f*Geo.radPerDeg);
					break;
				}
				case 2:{
					mat.fromZRotation(5.f*Geo.radPerDeg);
					break;
				}
				}
				mat.scale(oldScale + (newScale - oldScale)*j/20.f);
				mat.translate(-0.3f, -0.25f, 0.f);
				viewport.setViewPerGlobal(mat);
				viewport.renderNow();
			}
		}
	}
}