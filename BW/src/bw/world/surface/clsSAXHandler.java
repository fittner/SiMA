package bw.world.surface;


import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import sim.field.grid.IntGrid2D;

//@deprecated
//Properties are to be used instead of XML-files

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
public class clsSAXHandler extends DefaultHandler
{
	protected IntGrid2D moSurfaceGrid;
	protected int mnWidth = -1, mnHeight = -1; 
	
	/**
	 * Overwritten from the super class
	 */
	@Override
	public void startDocument()
	{
		System.out.println("SAXHandler: \nStarting file processing...");
	}

	/**
	 * Overwritten from the super class
	 */
	@Override
	public void endDocument()
	{
		System.out.println("Done processing file.");
	}
	
	/**
	 * In this class the world is created form the XML-file. A basic range check is done to make sure the coordinates are within the boundaries. 
	 * Overwritten from the super class
	 */
	@Override
	public void startElement(String poUri, String poLocalName, String poQName, Attributes poAttr)
	{
		//creating the world with the provided width and height
		if (poQName.equals("world"))
		{
			int nAttrCount = poAttr.getLength();
			
			//get the attributes of the element
			for (int i = 0; i < nAttrCount; i++)
			{
				if (poAttr.getQName(i).equals("width"))
					mnWidth = Integer.valueOf(poAttr.getValue(i)).intValue();
				if (poAttr.getQName(i).equals("height"))
					mnHeight = Integer.valueOf(poAttr.getValue(i)).intValue();
			}
			
			//create surfaces
			moSurfaceGrid = new IntGrid2D(mnWidth, mnHeight);
			moSurfaceGrid.setTo(0);
		}
		//sets the world to a default surface. 
		else if (poQName.equals("defaultSurface"))
		{
			//get the attributes of the element
			if (poAttr.getQName(0).equals("defaultValue"))
				 moSurfaceGrid.setTo(Integer.valueOf(poAttr.getValue(0)).intValue());
		}
		//sets an area to a specified surface
		else if(poQName.equals("surfaceArea"))
		{
			int nStartX = -1, nStartY = -1, nEndX = -1, nEndY = -1, nSurface = -1;
			int nAttrCount = poAttr.getLength();
			
			//get the attributes of the element
			for (int i = 0; i < nAttrCount; i++)
			{
				if (poAttr.getQName(i).equals("startX"))
					nStartX = Integer.valueOf(poAttr.getValue(i)).intValue();
				if (poAttr.getQName(i).equals("startY"))
					nStartY = Integer.valueOf(poAttr.getValue(i)).intValue();
				if (poAttr.getQName(i).equals("endX"))
					nEndX = Integer.valueOf(poAttr.getValue(i)).intValue();
				if (poAttr.getQName(i).equals("endY"))
					nEndY = Integer.valueOf(poAttr.getValue(i)).intValue();
				if (poAttr.getQName(i).equals("surface"))
					nSurface = Integer.valueOf(poAttr.getValue(i)).intValue();
				
			}
			
			//check the boundaries of the world and set the surfaces
			if (nStartX >= 0 && nStartY >= 0 && nEndX >= 0 && nEndY >= 0 && 
					nStartX < mnWidth && nStartY < mnHeight && nEndX <= mnWidth && 
					nEndY <= mnHeight && nSurface >= 0 && nSurface < itfSurface.NUMBEROFSURFACES)
			{
				for (int i = nStartX; i < nEndX; i++)
					for (int j = nStartY; j < nEndY; j++)
						moSurfaceGrid.set(i, j, nSurface);
			}
		}
		//sets a tile to a specified surface.
		else if (poQName.equals("surface"))
		{
			int nX = -1, nY = -1, nSurface = -1;
			int nAttrCount = poAttr.getLength();
			
			//get the attributes of the element
			for (int i = 0; i < nAttrCount; i++)
			{
				if (poAttr.getQName(i).equals("x"))
					nX = Integer.valueOf(poAttr.getValue(i)).intValue();
				if (poAttr.getQName(i).equals("y"))
					nY = Integer.valueOf(poAttr.getValue(i)).intValue();
				if (poAttr.getQName(i).equals("surface"))
					nSurface = Integer.valueOf(poAttr.getValue(i)).intValue();
			}
			
			//check the boundaries of the world and set the surfaces
			if (nX >= 0 && nY >= 0 && nSurface >= 0 && nX < mnWidth && nY < mnHeight && nSurface < itfSurface.NUMBEROFSURFACES)
			{
				moSurfaceGrid.set(nX, nY, nSurface);
			}
			else
			{
				System.out.println("Erroneous entry for a surface: \nx: " + nX + "\ny: " + nY + "\nsurface: " + nSurface);
			}
		}
	}
	
	/**
	 * Overwritten from the super class
	 */
	@Override
	public void endElement(String poUri, String poLocalName, String poQName)
	{
		
	}
	
	/**
	 * Returns the surface created from the XML-file (or null if the processing was not successful).
	 * @return
	 */
	public IntGrid2D getSurfaceGrid()
	{
		return moSurfaceGrid;
	}
}
