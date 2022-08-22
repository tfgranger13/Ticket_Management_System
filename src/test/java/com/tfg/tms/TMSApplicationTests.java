package com.tfg.tms;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

// @formatter:off
@Suite
@SelectPackages({
	"com.tfg.tms.test.dao",
	"com.tfg.tms.test.service" })
public class TMSApplicationTests {

}
// @formatter:on