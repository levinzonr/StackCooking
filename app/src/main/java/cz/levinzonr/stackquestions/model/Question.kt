package cz.levinzonr.stackquestions.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by nomers on 3/15/18.
 */
class Question(
        val questionId: Int,
        val title: String,
        val answerCount: Int,
        val isAnswered: Boolean,
        val link: String,
        val body: String,
        val tags: Array<String>,
        val viewCount: Int,
        val owner: User?
) : Parcelable {


    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArray(),
            parcel.readInt(),
            parcel.readParcelable(User::class.java.classLoader)) {
    }

    override fun toString(): String {
        return "Question(questionId=$questionId, title='$title', answerCount=$answerCount, " +
                "isAnswered=$isAnswered, link='$link', tags=${Arrays.toString(tags)}, " +
                "viewCount=$viewCount, owner=$owner)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(questionId)
        parcel.writeString(title)
        parcel.writeInt(answerCount)
        parcel.writeByte(if (isAnswered) 1 else 0)
        parcel.writeString(link)
        parcel.writeString(body)
        parcel.writeStringArray(tags)
        parcel.writeInt(viewCount)
        parcel.writeParcelable(owner, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}