package cz.levinzonr.stackquestions.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by nomers on 3/17/18.
 */
class User(
        val userId: Int?,
        val displayName: String?,
        val profileImage: String?


) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString()) {
    }

    override fun toString(): String {
        return "User(userId=$userId, displayName=$displayName, profileImage=$profileImage)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(userId)
        parcel.writeString(displayName)
        parcel.writeString(profileImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}