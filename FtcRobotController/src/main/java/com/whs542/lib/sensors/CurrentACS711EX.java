package com.whs542.lib.sensors;

import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CurrentACS711EX implements HardwareDevice
{
	private double COUNTS_TO_AMPS = 1.0D;
	private DeviceInterfaceModule module = null;
	private int physicalPort = -1;
	private double offset = 0.0;

	public CurrentACS711EX(HardwareMap sensorMap, int physicalPort)
	{
		this.module = sensorMap.deviceInterfaceModule.get("cdim");
		this.physicalPort = physicalPort;
	}

	public void setOffset()
	{
		for(int i=0; i<5; i++)
		{
			offset += (double)this.module.getAnalogInputValue(this.physicalPort)/5.0;
		}
	}

	public String getDeviceName()
	{
		return "ACS711EX Current Sensor";
	}

	public String getConnectionInfo()
	{
		return this.module.getConnectionInfo() + "; analog port " + this.physicalPort;
	}

	public int getVersion()
	{
		return 1;
	}

	public void close()
	{

	}

	public double getCurrentValue()
	{
		return COUNTS_TO_AMPS * this.getRawValue();
	}

	public double getRawValue()
	{
		return this.module.getAnalogInputValue(this.physicalPort) - (int)offset;
	}
}