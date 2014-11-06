package ru.koleslena.gis


object Distance {
	def apply(longitude1: Double, latitude1: Double)(longitude: Double, latitude: Double) = {
		Utils.R * Math.acos(Math.sin(latitude) * Math.sin(latitude1) + 
							Math.cos(latitude) * Math.cos(latitude1) * Math.cos(longitude - longitude1))
	}
}