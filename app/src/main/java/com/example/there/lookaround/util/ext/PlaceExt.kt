package com.example.there.lookaround.util.ext

import com.example.domain.repo.model.SavedPlace
import com.example.there.lookaround.model.UIPlace
import com.example.there.lookaround.model.UISimplePlace
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLngBounds

val List<UISimplePlace>.latLngBounds: LatLngBounds
    get() = LatLngBounds.builder().also { builder ->
        forEach { builder.include(it.latLng) }
    }.build()

val Place.savedPlace: SavedPlace
    get() = SavedPlace(
        id,
        name.toString(),
        latLng,
        address?.toString(),
        phoneNumber?.toString(),
        websiteUri?.toString(),
        rating,
        placeTypes
    )

val Place.ui: UIPlace
    get() = UIPlace(
        id,
        name.toString(),
        latLng,
        address?.toString(),
        phoneNumber?.toString(),
        websiteUri?.toString(),
        rating,
        placeTypes
    )

val UIPlace.placeTypesNames: List<String>
    get() = placeTypes.asSequence().map {
        when (it) {
            0 -> "Other"
            1 -> "Accounting"
            2 -> "Airport"
            3 -> "Amusement Park"
            4 -> "Aquarium"
            5 -> "Art Gallery"
            6 -> "Atm"
            7 -> "Bakery"
            8 -> "Bank"
            9 -> "Bar"
            10 -> "Beauty Salon"
            11 -> "Bicycle Store"
            12 -> "Book Store"
            13 -> "Bowling Alley"
            14 -> "Bus Station"
            15 -> "Cafe"
            16 -> "Campground"
            17 -> "Car Dealer"
            18 -> "Car Rental"
            19 -> "Car Repair"
            20 -> "Car Wash"
            21 -> "Casino"
            22 -> "Cemetery"
            23 -> "Church"
            24 -> "City Hall"
            25 -> "Clothing Store"
            26 -> "Convenience Store"
            27 -> "Courthouse"
            28 -> "Dentist"
            29 -> "Department Store"
            30 -> "Doctor"
            31 -> "Electrician"
            32 -> "Electronics Store"
            33 -> "Embassy"
            34 -> "Establishment"
            35 -> "Finance"
            36 -> "Fire Station"
            37 -> "Florist"
            38 -> "Food"
            39 -> "Funeral Home"
            40 -> "Furniture Store"
            41 -> "Gas Station"
            42 -> "General Contractor"
            43 -> "Grocery Or Supermarket"
            44 -> "Gym"
            45 -> "Hair Care"
            46 -> "Hardware Store"
            47 -> "Health"
            48 -> "Hindu Temple"
            49 -> "Home Goods Store"
            50 -> "Hospital"
            51 -> "Insurance Agency"
            52 -> "Jewelry Store"
            53 -> "Laundry"
            54 -> "Lawyer"
            55 -> "Library"
            56 -> "Liquor Store"
            57 -> "Local Government Office"
            58 -> "Locksmith"
            59 -> "Lodging"
            60 -> "Meal Delivery"
            61 -> "Meal Takeaway"
            62 -> "Mosque"
            63 -> "Movie Rental"
            64 -> "Movie Theater"
            65 -> "Moving Company"
            66 -> "Museum"
            67 -> "Night Club"
            68 -> "Painter"
            69 -> "Park"
            70 -> "Parking"
            71 -> "Pet Store"
            72 -> "Pharmacy"
            73 -> "Physiotherapist"
            74 -> "Place Of Worship"
            75 -> "Plumber"
            76 -> "Police"
            77 -> "Post Office"
            78 -> "Real Estate Agency"
            79 -> "Restaurant"
            80 -> "Roofing Contractor"
            81 -> "Rv Park"
            82 -> "School"
            83 -> "Shoe Store"
            84 -> "Shopping Mall"
            85 -> "Spa"
            86 -> "Stadium"
            87 -> "Storage"
            88 -> "Store"
            89 -> "Subway Station"
            90 -> "Synagogue"
            91 -> "Taxi Stand"
            92 -> "Train Station"
            93 -> "Travel Agency"
            94 -> "University"
            95 -> "Veterinary Care"
            96 -> "Zoo"
            1001, 1002, 1003 -> "Administrative Area Level 1"
            1004 -> "Colloquial Area"
            1005 -> "Country"
            1006 -> "Floor"
            1007 -> "Geocode"
            1008 -> "Intersection"
            1009 -> "Locality"
            1010 -> "Natural Feature"
            1011 -> "Neighborhood"
            1012 -> "Political"
            1013 -> "Point Of Interest"
            1014 -> "Post Box"
            1015 -> "Postal Code"
            1016 -> "Postal Code Prefix"
            1017 -> "Postal Town"
            1018 -> "Premise"
            1019 -> "Room"
            1020 -> "Route"
            1021 -> "Street Address"
            1022, 1023, 1024, 1025, 1026, 1027 -> "Sublocality"
            1028 -> "Subpremise"
            1029 -> "Synthetic Geocode"
            1030 -> "Transit Station"
            else -> ""
        }
    }.filter { it.isNotEmpty() }.toList()