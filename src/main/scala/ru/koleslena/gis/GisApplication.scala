package ru.koleslena.gis

import java.io.FileWriter
import java.io.IOException
import scala.collection.mutable.PriorityQueue
import scala.io.Source
import ru.koleslena.gis.model.AptekaDistance
import ru.koleslena.gis.model.Point
import ru.koleslena.gis.model.Distance

object GisApplication extends App {
	if(args.length != 3) 
		throw new IllegalArgumentException
	
	try {
		val filename = args(0)
		val lon = args(1).toDouble
		val lat = args(2).toDouble
	   
		val queue: PriorityQueue[AptekaDistance] = new PriorityQueue()

		def convert = (x: Double) => x * Math.PI / 180
		
		val distance = Distance(convert(lon), convert(lat))( _: Double, _: Double)
		
		Source.fromFile(filename).getLines().drop(1).foreach { res =>
		   	try {
		   		val resource = res.split(Utils.parsedelimeter)
		   		val apd = new AptekaDistance(resource(0), resource(1), distance(convert(resource(2).toDouble), convert(resource(3).toDouble)))
		   		if(queue.length < Utils.capacity || (queue.length >= Utils.capacity && queue.head.compare(apd) > 0)) {
					queue.+=(apd)
				}
				if(queue.length > Utils.capacity)
					queue.dequeue()
		   	} catch {
		   	  	case t: Exception => None
		   	}
		}
		
		queue.foreach(println)
		
		val fw = new FileWriter("result.csv", false)
		
		try {
			fw.write(queue.reverse.mkString("\n"))
		} catch {
			case t: IOException => throw new IOException(t)  
		}
		finally fw.close() 
	
	} catch {
		case t: Exception => throw new IllegalArgumentException(t) 
	}
	  
}