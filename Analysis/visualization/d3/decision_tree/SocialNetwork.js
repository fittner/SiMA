/*
	original code from
http://bl.ocks.org/d3noob/8324872 
*/

//var treeData = treeData;

// ************** Generate the tree diagram	 *****************
var margin = {top: 20, right: 120, bottom: 20, left: 150},
 //	width = 960 - margin.right - margin.left,
	width = 8000 - margin.right - margin.left,
	height = 880 - margin.top - margin.bottom;
	

var tree = d3.layout.tree()
	.size([height, width]);

var diagonal = d3.svg.diagonal()
	.projection(function(d) { return [d.y, d.x]; });
	
	
var i = 0;

var svg = d3.select("body").append("svg")
	.attr("width", width + margin.right + margin.left)
	.attr("height", height + margin.top + margin.bottom)
  .append("g")
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")")
	 
	;

d3.json("socialNetwork.json", function update(source) {

  // Compute the new tree layout.
  var nodes = tree.nodes(source),
	  links = tree.links(nodes);
  
  // Normalize for fixed-depth.
  nodes.forEach(function(d) { d.y = (110 * d.position ); /*console.log(d.depth);*/});  // took out d.depth

  // Declare the nodes�
  var node = svg.selectAll("g.node")
	  .data(nodes, function(d) { return d.id || (d.id = ++i); });

  // Enter the nodes.
  var nodeEnter = node.enter().append("g")
	  .attr("class", "node")
	  .attr("transform", function(d) { 
		  return "translate(" + d.y + "," + d.x + ")"; });
		

  nodeEnter.append("circle")
	  .attr("r", function(d) { return d.raw_value * 50; })
	  .style("stroke", function(d) { return d.type; })
	  .style("fill", function(d) { return d.level; })
	  ;

 /* nodeEnter.append("text")
	  .attr("x", function(d) { 
		  return d.children || d._children ? 
		 // (d.value + 4) * -1 : d.value + 4 })
		 d.value-25:d.value-25 })
		 
	  .attr("dy", ".35em")
	  .attr("y", function(d) {
	   return (d.value +10);
	  })
	//  .attr("text-anchor", function(d) { 
	//	  return d.children || d._children ? "end" : "start"; })
	  .text(function(d) { return (d.name); })
	  .style("fill-opacity", 1)
	  .style("fill", function(d) { return d.nameColor;})
	 ;
	 
    nodeEnter.append("text")
	  .attr("x", function(d) { 
		  return d.children || d._children ? 
		 // (d.value + 4) * -1 : d.value + 4 })
		 d.value-50:d.value-50 })
		 
	  .attr("dy", ".35em")
	  .attr("y", function(d) {
	   return (d.value +30);
	  })
	//  .attr("text-anchor", function(d) { 
	//	  return d.children || d._children ? "end" : "start"; })
	  .text(function(d) { return ("("+d.groupname+")"); })
	  .style("fill-opacity", 1)
	  .style("fill", function(d) { return d.nameColor;})
	 ;	 */
	 /** MUCH MUCH BETTER Positioning **/ 
	  nodeEnter.append("text")
	              .attr("class","nodetext")
							.text(function(d) { return (d.name); })
                            .attr("dx", 0)	
                            .attr("dy", function(d) { return 16 + (d.raw_value * 50); })
                            .style("font-size","5px")
                            .attr("text-anchor", "middle")
							.style("fill", function(d) { return d.nameColor;})            
                            .style("font-size","12px")
	
	 ;
	 
    nodeEnter.append("text")
	 					.text(function(d) { return (d.value); })
                            .attr("dx", 0)	
                            .attr("dy", function(d) { return 32 + (d.raw_value * 50); })
                            .style("font-size","5px")
                            .attr("text-anchor", "middle")
							.style("fill", function(d) { return d.nameColor;})            
                            .style("font-size","12px")
	 
	 

  // Declare the links�
  var link = svg.selectAll("path.link")
	  .data(links, function(d) { return d.target.id; });

  // Enter the links.
  link.enter().insert("path", "g")
	  .attr("class", "link")
  	  .style("stroke", function(d) { return d.target.message; })
	  .attr("d", diagonal);
});


