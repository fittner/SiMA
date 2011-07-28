/**
 * EdgeList.java: BW - bw.world.surface
 * 
 * @author kohlhauser
 * 01.10.2009, 15:23:13
 */
package bw.world.surface;

/**
 * DOCUMENT (kohlhauser) - insert description 
 * 
 * @author kohlhauser
 * 01.10.2009, 15:23:13
 * 
 */
public class EdgeList 
{
	private Edge head;

	public EdgeList ()
	{
		head = null;
	}

	/**
	 * Insert new basic edge.
	 * 
	 * @param yUpper geometry information
	 * @param xIntersect geometry information
	 * @param dxPerScan geometry information
	 */

	public void insert (int yUpper, double xIntersect, double dxPerScan)
	{
		insert (new Edge (yUpper, xIntersect, dxPerScan));
	}	

	/**
	 * Insert edge at the right place in the list.
	 */

	public void insert (Edge e)
	{
		if (head == null)
		{
			e.prev = null;
			e.next = null;
			head   = e;
		}
		else if (head.xIntersect > e.xIntersect) // first element is already bigger
		{
			e.prev	= null;
			e.next	= head;
			head.prev = e;
			head	  = e;
		}
		else
		{
			Edge q = head;
		
			while ((q.next != null) && (e.xIntersect > q.next.xIntersect)) q = q.next;

			e.prev = q;
			e.next = q.next;
			if (q.next != null) q.next.prev = e;
			q.next = e;
		}
	}

	/**
	 * Delete edge from the list.
	 */

	private void delete (Edge e)
	{
		if (head != null)
		{
			if ((e.prev == null) && (e.next == null))
			{
				head = null;
			}
			else if (e.prev == null)
			{
				e.next.prev = null;
				head = e.next;
			}
			else if (e.next == null)
			{
				e.prev.next = null;
			}
			else
			{
				e.prev.next = e.next;
				e.next.prev = e.prev;
			}
		}
	}

	/**
	 * Update list based on a scanline.
	 * Remove edges that are completed on this scanline.
	 */

	public void update (int scan)
	{
		for (Edge q = head; q != null; q = q.next)
		{
			if (scan >= q.yUpper)
			{
				delete (q);
			}
			else
			{
				q.xIntersect += q.dxPerScan;
			}
		}
	}

	/**
	 * Resort edges.
	 * This is necessary when edges cross after moving to the next scanline.
	 */

	public void resort ()
	{
		if (head != null)
		{
			Edge q, p = head.next;

			head.next = null;

			while (p != null)
			{
				q = p.next;
				insert (p);
				p = q;
			}
		}
	}

	/**
	 * Get first edge in the list.
	 */

	public Edge getHead ()
	{
		return head;
	}

	/**
	 * Empty list?
	 */

	public boolean isEmpty()
	{
		return (head == null);
	}

	/**
	 * Debug output.
	 */

	public void print ()
	{
		for (Edge e = head; e != null; e = e.next)
		{
			System.out.println (e.yUpper + ", " + e.xIntersect + ", " + e.dxPerScan);
		}
	}
}
