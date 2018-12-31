package com.example.there.aroundmenow.places.placetypes.list

import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.model.UIPlaceType
import com.example.there.aroundmenow.model.UIPlaceTypeGroup

internal val placeTypeGroups: List<UIPlaceTypeGroup> = listOf(
    UIPlaceTypeGroup(
        "Food & drinks", listOf(
            UIPlaceType("[amenity=bar]", "Bar", R.drawable.bar),
            UIPlaceType("[amenity=cafe]", "Cafe", R.drawable.cafe),
            UIPlaceType("[amenity=fast_food]", "Fast food", R.drawable.fast_food),
            UIPlaceType("[amenity=pub]", "Pub", R.drawable.pub),
            UIPlaceType("[amenity=restaurant]", "Restaurant", R.drawable.restaurant)
        ),
        R.drawable.food
    ),
    UIPlaceTypeGroup(
        "Education", listOf(
            UIPlaceType("[amenity=college]", "College", R.drawable.college),
            UIPlaceType("[amenity=library]", "Library", R.drawable.library),
            UIPlaceType("[amenity=school]", "School", R.drawable.school),
            UIPlaceType("[amenity=university]", "University", R.drawable.university)
        ),
        R.drawable.education
    ),
    UIPlaceTypeGroup(
        "Transport", listOf(
            UIPlaceType("[amenity=bicycle_rental]", "Bike rental", R.drawable.bike_rental),
            UIPlaceType("[amenity=bus_station]", "Bus station", R.drawable.bus_station),
            UIPlaceType("[amenity=car_rental]", "Car rental", R.drawable.car_rental),
            UIPlaceType("[amenity=fuel]", "Gas station", R.drawable.gas_station),
            UIPlaceType("[amenity=parking]", "Parking", R.drawable.parking),
            UIPlaceType("[amenity=taxi]", "Taxi", R.drawable.taxi),
            UIPlaceType("[aeroway=aerodrome]", "Airport", R.drawable.airport),
            UIPlaceType("[railway=station]", "Train station", R.drawable.train_station),
            UIPlaceType("[railway=subway]", "Subway", R.drawable.subway)
        ),
        R.drawable.transport
    ),
    UIPlaceTypeGroup(
        "Financial", listOf(
            UIPlaceType("[amenity=atm]", "ATM", R.drawable.atm),
            UIPlaceType("[amenity=bank]", "Bank", R.drawable.bank)
        ),
        R.drawable.financial
    ),
    UIPlaceTypeGroup(
        "Healthcare", listOf(
            UIPlaceType("[amenity=dentist]", "Dentist", R.drawable.dentist),
            UIPlaceType("[amenity=doctors]", "Doctor's", R.drawable.doctor),
            UIPlaceType("[amenity=hospital]", "Hospital", R.drawable.hospital),
            UIPlaceType("[amenity=pharmacy]", "Pharmacy", R.drawable.pharmacy),
            UIPlaceType("[amenity=veterinary]", "Vet", R.drawable.veterinary)
        ),
        R.drawable.healthcare
    ),
    UIPlaceTypeGroup(
        "Entertainment", listOf(
            UIPlaceType("[amenity=arts_centre]", "Museum", R.drawable.arts_centre),
            UIPlaceType("[amenity=casino]", "Casino", R.drawable.casino),
            UIPlaceType("[amenity=cinema]", "Cinema", R.drawable.cinema),
            UIPlaceType("[amenity=nightclub]", "Nightclub", R.drawable.nightclub),
            UIPlaceType("[amenity=theatre]", "Theatre", R.drawable.theatre)
        ),
        R.drawable.entertainment
    ),
    UIPlaceTypeGroup(
        "Shopping", listOf(
            UIPlaceType("[amenity=marketplace]", "Marketplace", R.drawable.marketplace),
            UIPlaceType("[shop=convenience]", "Local store", R.drawable.convenience_store),
            UIPlaceType("[shop=general]", "General store", R.drawable.general_store),
            UIPlaceType("[shop=department_store]", "Department store", R.drawable.department_store),
            UIPlaceType("[shop=supermarket]", "Supermarket", R.drawable.supermarket)
        ),
        R.drawable.shopping
    ),
    UIPlaceTypeGroup(
        "Pray", listOf(
            UIPlaceType("[building=church]", "Church", R.drawable.church),
            UIPlaceType("[shop=mosque]", "Mosque", R.drawable.convenience_store),
            UIPlaceType("[shop=synagogue]", "Synagogue", R.drawable.general_store)
        ),
        R.drawable.pray
    )
)