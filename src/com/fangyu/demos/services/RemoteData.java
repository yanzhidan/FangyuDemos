package com.fangyu.demos.services;

import android.os.Parcel;
import android.os.Parcelable;

public class RemoteData implements Parcelable
{
	public int id;
	public String name;

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(id);
		dest.writeString(name);
	}
	
	public static final Parcelable.Creator<RemoteData> CREATOR = new Creator<RemoteData>()
	{
		@Override
		public RemoteData[] newArray(int size)
		{
			return new RemoteData[size];
		}
		
		@Override
		public RemoteData createFromParcel(Parcel source)
		{
			RemoteData data = new RemoteData();
			data.id = source.readInt();
			data.name = source.readString();
			return data;
		}
	};
}
