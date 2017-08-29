package com.amx.dataservice.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml"})
@Transactional
@TransactionConfiguration(transactionManager="",defaultRollback = true)
public class ProductTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
