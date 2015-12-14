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
package com.payintech.smoney.enumeration;

import com.google.gson.annotations.SerializedName;

/**
 * HistoryTypeEnum.
 *
 * @author Jean-Pierre Boudic
 * @version 15.11
 * @since 15.11
 */
//TODO: Deduction faite à partir des résultats...
// Voir : https://rest-pp.s-money.fr/api/sandbox/users/SMoney-testing-001/historyitems
public enum HistoryTypeEnum {

    @SerializedName("0")
    PAYMENT, //Transfert compte à compte
    @SerializedName("1")
    CARD, //Payement par CB
    @SerializedName("2")
    MONEYOUT //Payement par virement bancaire
}
