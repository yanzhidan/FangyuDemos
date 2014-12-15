package com.fangyu.demos.services;

import com.fangyu.demos.services.IRemoteData;
import com.fangyu.demos.services.IRemoteServiceCallback;
interface IRemoteService
{
	void remoteAction();
	int getPid();
	IRemoteData getRemoteData(in IRemoteData data);
	
	/**
     * Often you want to allow a service to call back to its clients.
     * This shows how to do so, by registering a callback interface with
     * the service.
     */
	void registerCallback(IRemoteServiceCallback callback); 
	
	/**
     * Remove a previously registered callback interface.
     */
	void unregisterCallback(IRemoteServiceCallback callback);
}