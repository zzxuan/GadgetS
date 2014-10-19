package com.gadgets.flashlight;

import android.hardware.Camera;

public class Flashlight {
	private static Flashlight flashlight;

	public static Flashlight getIntance() {
		if (flashlight == null)
			flashlight = new Flashlight();
		return flashlight;
	}

	private Flashlight() {

	}

	private Camera m_Camera;
	private boolean m_isOn;

	public void clickFlashlight() {
		if (!m_isOn) {
			turnOn();
		} else {
			turnOff();
		}
	}

	public void turnOn() {
		if (!m_isOn) {
			m_isOn = true;
			try {
				m_Camera = Camera.open();
				Camera.Parameters mParameters;
				mParameters = m_Camera.getParameters();
				mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				m_Camera.setParameters(mParameters);
			} catch (Exception ex) {
			}
		}
	}

	public void turnOff() {
		if (m_isOn) {
			m_isOn = false;
			try {
				Camera.Parameters mParameters;
				mParameters = m_Camera.getParameters();
				mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				m_Camera.setParameters(mParameters);
				m_Camera.release();
			} catch (Exception ex) {
			}
		}
	}
}
