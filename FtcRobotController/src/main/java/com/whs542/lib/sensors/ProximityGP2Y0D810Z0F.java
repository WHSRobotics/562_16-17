package com.whs542.lib.sensors;

import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ProximityGP2Y0D810Z0F implements HardwareDevice
{
	private DeviceInterfaceModule module = null;
	private int physicalPort = -1;

	public ProximityGP2Y0D810Z0F(HardwareMap sensorMap, int physicalPort)
	{
		this.module = sensorMap.deviceInterfaceModule.get("cdim");
		this.physicalPort = physicalPort;
	}

	public String getDeviceName()
	{
		return "GP2Y0D810Z0F Proximity Sensor";
	}

	public String getConnectionInfo()
	{
		return this.module.getConnectionInfo() + "; digital port " + this.physicalPort;
	}

	public int getVersion()
	{
		return 1;
	}

	public void close()
	{

	}

	public double getValue()
	{
		return this.isInDistance()?1.0D:0.0D;
	}

	public boolean isInDistance()
	{
		return !(this.module.getDigitalChannelState(this.physicalPort));
	}
}