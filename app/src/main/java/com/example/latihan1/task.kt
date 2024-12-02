package com.example.latihan1

import android.os.Parcel
import android.os.Parcelable


data class task(
    var namaTask: String,
    var tanggal: String,
    var kategori: String,
    var deskripsi: String,
    var isCompleted: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namaTask)
        parcel.writeString(tanggal)
        parcel.writeString(kategori)
        parcel.writeString(deskripsi)
        parcel.writeByte(if (isCompleted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<task> {
        override fun createFromParcel(parcel: Parcel): task {
            return task(parcel)
        }

        override fun newArray(size: Int): Array<task?> {
            return arrayOfNulls(size)
        }
    }
}
