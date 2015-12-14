/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 - 2015 PayinTech
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.payintech.smoney;

import com.payintech.smoney.entity.PaymentEntity;
import com.payintech.smoney.entity.SubAccountEntity;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import retrofit.Call;
import retrofit.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * PaymentTest.
 *
 * @author Jean-Pierre Boudic
 * @version 15.11
 * @since 15.11
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaymentTest {

    public static SMoneyService service = SMoneyServiceFactory.createService();
    public static PaymentEntity payment;
    private static Boolean TEST_CREATION = true;

    @Test
    public void payment_001_list() throws IOException {
        Call<List<PaymentEntity>> listCall = service.listPayments(TestSettings.testUserAppUserId);
        Response<List<PaymentEntity>> response = listCall.execute();
        if (response.code() != 200) {
            System.out.println(response.errorBody().string());
        }
        Assert.assertEquals(response.code(), 200);
        List<PaymentEntity> payments = response.body();
        Assert.assertTrue(payments.size() > 0);
        if (payments.size() > 0) {
            PaymentTest.payment = payments.get(0);
        }
    }

    @Test
    public void payment_002_get() throws IOException {
        if (PaymentTest.payment == null) {
            System.out.println("Not Payment for diff...");
        }
        Assert.assertNotNull(PaymentTest.payment);
        Call<PaymentEntity> call = service.getPayment(TestSettings.testUserAppUserId, PaymentTest.payment.OrderId);
        Response<PaymentEntity> response = call.execute();
        if (response.code() != 200) {
            System.out.println(response.errorBody().string());
        }
        Assert.assertEquals(response.code(), 200);
        PaymentEntity payment = response.body();
        Assert.assertEquals(PaymentTest.payment.OrderId, payment.OrderId);
        Assert.assertEquals(PaymentTest.payment.Message, payment.Message);
        Assert.assertEquals(PaymentTest.payment.Amount, payment.Amount);
        Assert.assertEquals(PaymentTest.payment.PaymentDate, payment.PaymentDate);
        Assert.assertEquals(PaymentTest.payment.Id, payment.Id);
        Assert.assertEquals(PaymentTest.payment.Sender.AppAccountId, payment.Sender.AppAccountId);
    }

    @Test
    public void payment_003_create() throws IOException {
        if (!TEST_CREATION) {
            return;
        }
        PaymentEntity p = new PaymentEntity();
        p.OrderId = String.format("Test-%s", UUID.randomUUID().toString().split("-")[0]);
        p.Beneficiary = new SubAccountEntity();
        p.Beneficiary.AppAccountId = TestSettings.testUserAppUserId;
        p.Amount = 20L;
        p.Message = "Test";

        Call<PaymentEntity> call = service.createPayment(TestSettings.testCompanyAppUserId, p);
        Response<PaymentEntity> response = call.execute();
        if (response.code() != 201) {
            System.err.println(response.errorBody().string());
        }
        Assert.assertEquals(response.code(), 201);
        PaymentEntity payment = response.body();

        Assert.assertEquals(payment.OrderId, p.OrderId);
        Assert.assertEquals(payment.Beneficiary.AppAccountId, p.Beneficiary.AppAccountId);
        Assert.assertEquals(payment.Message, p.Message);
        Assert.assertEquals(payment.Amount, p.Amount);
        Assert.assertTrue(payment.Id > 0);
    }

    @Test
    public void payment_004_create_multiple() throws IOException {
        if (!TEST_CREATION) {
            return;
        }
        List<PaymentEntity> ps = new ArrayList<>();

        PaymentEntity p1 = new PaymentEntity();
        p1.OrderId = String.format("Test-%s", UUID.randomUUID().toString().split("-")[0]);
        p1.Beneficiary = new SubAccountEntity();
        p1.Beneficiary.AppAccountId = TestSettings.testCompanyAppUserId;
        p1.Sender = new SubAccountEntity();
        p1.Sender.AppAccountId = TestSettings.testUserAppUserId;
        p1.Amount = 10L;
        p1.Message = "Test";
        ps.add(p1);

        PaymentEntity p2 = new PaymentEntity();
        p2.OrderId = String.format("Test-%s", UUID.randomUUID().toString().split("-")[0]);
        p2.Beneficiary = new SubAccountEntity();
        p2.Beneficiary.AppAccountId = TestSettings.testCompanyAppUserId;
        p2.Sender = new SubAccountEntity();
        p2.Sender.AppAccountId = TestSettings.testUserAppUserId;
        p2.Amount = 10L;
        p2.Message = "Test";
        ps.add(p2);

        Call<List<PaymentEntity>> listCall = service.createPayments(TestSettings.testUserAppUserId, ps);
        Response<List<PaymentEntity>> response = listCall.execute();
        if (response.code() != 201) {
            System.err.println(response.errorBody().string());
        }
        Assert.assertEquals(response.code(), 201);
        List<PaymentEntity> payments = response.body();

        Assert.assertTrue(payments.size() == 2);

        PaymentEntity payment1 = payments.stream().filter(p -> p.OrderId.equals(p1.OrderId)).findFirst().get();
        Assert.assertEquals(payment1.OrderId, p1.OrderId);
        Assert.assertEquals(payment1.Beneficiary.AppAccountId, p1.Beneficiary.AppAccountId);
        Assert.assertEquals(payment1.Message, p1.Message);
        Assert.assertEquals(payment1.Amount, p1.Amount);
        Assert.assertTrue(payment1.Id > 0);

        PaymentEntity payment2 = payments.stream().filter(p -> p.OrderId.equals(p2.OrderId)).findFirst().get();
        Assert.assertEquals(payment2.OrderId, p2.OrderId);
        Assert.assertEquals(payment2.Beneficiary.AppAccountId, p2.Beneficiary.AppAccountId);
        Assert.assertEquals(payment2.Message, p2.Message);
        Assert.assertEquals(payment2.Amount, p2.Amount);
        Assert.assertTrue(payment2.Id > 0);
    }
}