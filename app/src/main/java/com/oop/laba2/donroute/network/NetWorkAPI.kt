package com.oop.laba2.donroute.network

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.oop.laba2.donroute.AllTransport
import com.oop.laba2.donroute.enteties.Transport
import com.oop.laba2.donroute.enteties.TransportType

object NetWorkAPI {
    val db = Firebase.firestore


    fun addToFavorite(uid:String,transport: AllTransport.FavoriteTransport){
        db.collection("profiles").document(uid)
            .update("favorite",FieldValue.arrayUnion(transport))
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun createFavoritePath(uid:String){

        val favorite = mapOf(Pair("favorite",listOf<Int>()))

        db.collection("profiles").document(uid)
            .set(favorite)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
    fun removeFromFavorite(uid:String,transport: AllTransport.FavoriteTransport){
        db.collection("profiles").document(uid)
            .update("favorite",FieldValue.arrayRemove(transport))
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun updateAllTransport(){
        db.collection("allTransport").document("allTransport")
            .set(hashMapOf(Pair("allTransport", AllTransport.allTransport)))
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getAllTransport(){
        db.collection("allTransport").document("allTransport")
            .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen error", e)
                    return@addSnapshotListener
                }

                val qwe =querySnapshot?.data?.get("allTransport") as List<HashMap<String,Any>>
                val list = mutableListOf<Transport>()
                qwe.forEach {
                    list.add(
                        Transport(
                            (it.get("id") as Long).toInt(),
                            it.get("timestamp") as String,
                            it.get("number") as String,
                            it.get("tuda") as List<String>,
                            it.get("obratno")as List<String>,
                            it.get("_isFavorite") as Boolean,
                            TransportType.valueOf(it.get("transportType") as String)
                        )
                    )
                }
                AllTransport.allTransport = list
            }

    }
    fun getAllFavorite(uid:String){
        db.collection("profiles").document(uid)
            .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen error", e)
                    return@addSnapshotListener
                }

                if (querySnapshot!!.get("favorite") == DocumentChange.Type.ADDED) {
                    Log.d(TAG, "New city: ${querySnapshot.data.toString()}")
                }

                val source = if (querySnapshot.metadata.isFromCache)
                    "local cache"
                else
                    "server"

                val favorite = querySnapshot.data!!.get("favorite")
                AllTransport.allTransport.forEach { transport ->
                    if((favorite as List<HashMap<String,String>>)
                            .any { it.get("number").toString() == transport.number  && TransportType.valueOf(it.get("type").toString()) == transport.transportType})
                        transport._isFavorite = true
                }

                Log.d(TAG, "Data fetched from $source")
            }

    }

    fun initCacheSaving(){

//        val settings1 = FirebaseFirestoreSettings.Builder()
//            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
//            .build()
//        db.firestoreSettings = settings1

        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        db.firestoreSettings = settings
    }
}