package ru.koleslena.gis

import java.io.FileWriter
import java.io.IOException
import scala.io.Source
import ru.koleslena.gis.model.AptekaDistance
import scala.collection.mutable.PriorityQueue
import ru.koleslena.gis.model.AptekaDistance

/**
 * second variant of solution
 */
object GisApplication extends App {
	if(args.length != 3) 
		throw new IllegalArgumentException
	
	def parseDouble(s: String) = try { Some(s.toDouble) } catch { case _: Throwable => None }
	
	val filename = args(0)
	val lonOp = parseDouble(args(1))
	val latOp = parseDouble(args(2))
	
	if(lonOp.isDefined && latOp.isDefined) {
	  
		val lon = lonOp.get
		val lat = latOp.get

		def convert = (x: Double) => x * Math.PI / 180
		
		val distance = Distance(convert(lon), convert(lat))( _: Double, _: Double)
		
		val lines = Source.fromFile(filename).getLines().drop(1)
		
		val queue: PriorityQueue[AptekaDistance] = lines.foldLeft(new PriorityQueue[AptekaDistance]())
		{ (q, el) =>
		    
		  val resource = el.split(Utils.parsedelimeter)
		  val apd = new AptekaDistance(resource(0), resource(1), distance(convert(resource(2).toDouble), convert(resource(3).toDouble)))
		  if(q.length < Utils.capacity || (q.length >= Utils.capacity && q.head.compare(apd) > 0)) {
			q.+=(apd)
			if(q.length > Utils.capacity)
				q.dequeue()
		  }
		  q
		}
		
		val fw = new FileWriter("result.csv", false)
		
		try {
			fw.write(queue.reverse.mkString("\n"))
		} catch {
			case t: IOException => throw new IOException(t)  
		}
		finally fw.close() 
	
	} else {
		throw new IllegalArgumentException("Longitude and latitude are not in double format") 
	}
}