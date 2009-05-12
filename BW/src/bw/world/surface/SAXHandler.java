package bw.world.surface;


import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import sim.field.grid.IntGrid2D;

/**
 * This class parses the XML-file in order to create a world with surfaces from it. 
 * The file is parsed using SAX. The file is attribute-normal for simplicity in processing. 
 * 
 * The .xsd-file looks as following: 
 * 
 * <?xml version="1.0" ?>
<schema>

	<annotation>
		<documentation>
			This document defines a schema for the surface of a bw world.
		</documentation>
	</annotation>
 
	<element name="world" type="WorldType"/>

	<complexType name="WorldType">
		<attribute name="width" type="nonNegativeInteger" use="required"/>
		<attribute name="height" type="nonNegativeInteger" use="required"/>
		<sequence>
			<element name="defaultSurface" type="defaultSurfaceType" use="optional"/>
			<element name="surfaceArea" type="surfaceAreaType" maxOccurs="unbounded" use="optional"/>
			<element name="surface" type="SurfaceType" maxOccurs="unbounded" use="optional"/>
		</sequence>
	</complexType>
	
	<complexType name="defaultSurfaceType">
		<attribute name="defaultValue" type="nonNegativeInteger" use="required"/>
	</complexType>
	
	<complexType name="surfaceAreaType">
		<attribute name="startX" type="nonNegativeInteger" use="required"/>
		<attribute name="startY" type="nonNegativeInteger" use="required"/>
		<attribute name="endX" type="nonNegativeInteger" use="required"/>
		<attribute name="endY" type="nonNegativeInteger" use="required"/>
		<attribute name="surface" type="nonNegativeInteger" use="required"/>
	</complexType>
	
	<complexType name="SurfaceType">
		<attribute name="x" type="nonNegativeInteger" use="required"/>
		<attribute name="y" type="nonNegativeInteger" use="required"/>
		<attribute name="surface" type="nonNegativeInteger" use="required"/>
	</complexType>
</schema>
 * @author kohlhauser
 *
 */
public class SAXHandler extends DefaultHandler
{
	protected IntGrid2D surfaceGrid;
	protected int width = -1, height = -1; 
	
	/**
	 * Overwritten from the super class
	 */
	public void startDocument()
	{
		System.out.println("SAXHandler: \nStarting file processing...");
	}

	/**
	 * Overwritten from the super class
	 */
	public void endDocument()
	{
		System.out.println("Done processing file.");
	}
	
	/**
	 * In this class the world is created form the XML-file. A basic range check is done to make sure the coordinates are within the boundaries. 
	 * Overwritten from the super class
	 */
	public void startElement(String uri, String localName, String qName, Attributes attr)
	{
		//creating the world with the provided width and height
		if (qName.equals("world"))
		{
			int attrCount = attr.getLength();
			
			//get the attributes of the element
			for (int i = 0; i < attrCount; i++)
			{
				if (attr.getQName(i).equals("width"))
					width = Integer.valueOf(attr.getValue(i)).intValue();
				if (attr.getQName(i).equals("height"))
					height = Integer.valueOf(attr.getValue(i)).intValue();
			}
			
			//create surfaces
			surfaceGrid = new IntGrid2D(width, height);
			surfaceGrid.setTo(0);
		}
		//sets the world to a default surface. 
		else if (qName.equals("defaultSurface"))
		{
			//get the attributes of the element
			if (attr.getQName(0).equals("defaultValue"))
				 surfaceGrid.setTo(Integer.valueOf(attr.getValue(0)).intValue());
		}
		//sets an area to a specified surface
		else if(qName.equals("surfaceArea"))
		{
			int startX = -1, startY = -1, endX = -1, endY = -1, surface = -1;
			int attrCount = attr.getLength();
			
			//get the attributes of the element
			for (int i = 0; i < attrCount; i++)
			{
				if (attr.getQName(i).equals("startX"))
					startX = Integer.valueOf(attr.getValue(i)).intValue();
				if (attr.getQName(i).equals("startY"))
					startY = Integer.valueOf(attr.getValue(i)).intValue();
				if (attr.getQName(i).equals("endX"))
					endX = Integer.valueOf(attr.getValue(i)).intValue();
				if (attr.getQName(i).equals("endY"))
					endY = Integer.valueOf(attr.getValue(i)).intValue();
				if (attr.getQName(i).equals("surface"))
					surface = Integer.valueOf(attr.getValue(i)).intValue();
				
			}
			
			//check the boundaries of the world and set the surfaces
			if (startX >= 0 && startY >= 0 && endX >= 0 && endY >= 0 && 
					startX < width && startY < height && endX <= width && 
					endY <= height && surface >= 0 && surface < Surface.NUMBEROFSURFACES)
			{
				for (int i = startX; i < endX; i++)
					for (int j = startY; j < endY; j++)
						surfaceGrid.set(i, j, surface);
			}
		}
		//sets a tile to a specified surface.
		else if (qName.equals("surface"))
		{
			int x = -1, y = -1, surface = -1;
			int attrCount = attr.getLength();
			
			//get the attributes of the element
			for (int i = 0; i < attrCount; i++)
			{
				if (attr.getQName(i).equals("x"))
					x = Integer.valueOf(attr.getValue(i)).intValue();
				if (attr.getQName(i).equals("y"))
					y = Integer.valueOf(attr.getValue(i)).intValue();
				if (attr.getQName(i).equals("surface"))
					surface = Integer.valueOf(attr.getValue(i)).intValue();
			}
			
			//check the boundaries of the world and set the surfaces
			if (x >= 0 && y >= 0 && surface >= 0 && x < width && y < height && surface < Surface.NUMBEROFSURFACES)
			{
				surfaceGrid.set(x, y, surface);
			}
			else
			{
				System.out.println("Erroneous entry for a surface: \nx: " + x + "\ny: " + y + "\nsurface: " + surface);
			}
		}
	}
	
	/**
	 * Overwritten from the super class
	 */
	public void endElement(String uri, String localName, String qName)
	{
		
	}
	
	/**
	 * Returns the surface created from the XML-file (or null if the processing was not successful).
	 * @return
	 */
	public IntGrid2D getSurfaceGrid()
	{
		return surfaceGrid;
	}
}
