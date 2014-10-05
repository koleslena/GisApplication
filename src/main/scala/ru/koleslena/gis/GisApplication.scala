package ru.koleslena.gis

import java.io.FileWriter
import java.io.IOException

import scala.collection.mutable.PriorityQueue
import scala.io.Source

import ru.koleslena.gis.model.AptekaDistance
import ru.koleslena.gis.model.Point

object GisApplication extends App {
	if(args.length != 3) 
		throw new IllegalArgumentException
	
	try {
		val filename = args(0)
		val lon = args(1).toDouble
		val lat = args(2).toDouble
	   
		val point = new Point(lon, lat)
	 
		val queue: PriorityQueue[AptekaDistance] = new PriorityQueue()

		Source.fromFile(filename).getLines().drop(1).foreach { res =>
		   	try {
		   		val apd = AptekaDistance(res.split(Utils.parsedelimeter), point)
		   		if(queue.length < Utils.capacity  || (queue.length >= Utils.capacity && queue.head.compare(apd) > 0)) {
					queue.+=(apd)
				}
				if(queue.length > Utils.capacity)
					queue.dequeue()
		   	} catch {
		   	  	case t: Exception => None
		   	}
		}
		
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