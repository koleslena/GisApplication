package ru.koleslena.gis.model

import ru.koleslena.gis.Utils

case class Point(longitude: Double, latitude: Double) extends AbstractPoint

abstract class AbstractPoint {
	val longitude: Double
	val latitude: Double

	def calcDist(point: AbstractPoint): Double = {
		Utils.R * Math.acos(Math.sin(latitude) * Math.sin(point.latitude) + 
							Math.cos(latitude) * Math.cos(point.latitude) * Math.cos(longitude - point.longitude))
	}
}