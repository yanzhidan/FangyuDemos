package com.fangyu.demos.services;

import android.os.Parcel;
import android.os.Parcelable;

public class IRemoteData implements Parcelable
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
	
	public static final Parcelable.Creator<IRemoteData> CREATOR = new Creator<IRemoteData>()
	{
		@Override
		public IRemoteData[] newArray(int size)
		{
			return new IRemoteData[size];
		}
		
		@Override
		public IRemoteData createFromParcel(Parcel source)
		{
			IRemoteData data = new IRemoteData();
			data.id = source.readInt();
			data.name = source.readString();
			return data;
		}
	};
}
