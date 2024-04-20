package com.example.finalproject.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.SerializedName

class Recipe : Parcelable {
    @SerializedName("uri")
    var id: String?

    @JvmField
    @SerializedName("label")
    var title: String?

    @JvmField
    @SerializedName("image")
    var img: String? = null

    @SerializedName("calories")
    var calories: String?

    @JvmField
    @SerializedName("ingredientLines")
    var ingredients: ArrayList<String>

    @JvmField
    @SerializedName("source")
    var takenFrom: String?

    @JvmField
    @SerializedName("cautions")
    var cautions: ArrayList<String>? = null

    @JvmField
    @SerializedName("healthLabels")
    var healthLabels: ArrayList<String>? = null


    constructor(
        id: String?,
        title: String?,
        calories: String?,
        ingredients: ArrayList<String>,
        takenFrom: String?,
        cautions: ArrayList<String>?,
        healthLabels: ArrayList<String>?,
    ) {
        this.id = id
        this.title = title
        this.calories = calories
        this.ingredients = ingredients
        this.takenFrom = takenFrom
        this.cautions = cautions
        this.healthLabels = healthLabels
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readString()
        title = `in`.readString()
        calories = `in`.readString()
        ingredients = ArrayList()
        `in`.readStringList(ingredients)
        takenFrom = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, i: Int) {
        dest.writeString(id)
        dest.writeString(title)
        dest.writeString(img)
        dest.writeString(calories)
        dest.writeStringList(ingredients)
        dest.writeString(takenFrom)
        dest.writeStringList(cautions)
        dest.writeStringList(healthLabels)
    }

//    companion object CREATOR : Creator<Recipe> {
//        override fun createFromParcel(parcel: Parcel): Recipe {
//            return Recipe(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Recipe?> {
//            return arrayOfNulls(size)
//        }
//    }
    companion object CREATOR : Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }

}
