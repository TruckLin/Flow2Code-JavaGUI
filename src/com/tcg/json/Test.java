package com.tcg.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.json.*;

public class Test {

	public static void main(String[] args) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile("/FlowDiagramDemo.json");
	
	}
}
