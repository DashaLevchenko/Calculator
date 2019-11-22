package Model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UnaryPercentTest {
    private Unary unary = new Unary();
    private Calculator calculator = new Calculator();

    @Test
    void percentInteger(){
        assertionPercentValid("0");

        assertionPercentValid("1");
        assertionPercentValid("-1");

        assertionPercentValid("2");
        assertionPercentValid("-2");

        assertionPercentValid("3");
        assertionPercentValid("-3");

        assertionPercentValid("4");
        assertionPercentValid("-4");

        assertionPercentValid("5");
        assertionPercentValid("-5");

        assertionPercentValid("6");
        assertionPercentValid("-6");

        assertionPercentValid("7");
        assertionPercentValid("-7");

        assertionPercentValid("8");
        assertionPercentValid("-8");

        assertionPercentValid("9");
        assertionPercentValid("-9");

        assertionPercentValid("10");
        assertionPercentValid("-10");

        assertionPercentValid("11");
        assertionPercentValid("-11");

        assertionPercentValid("14");
        assertionPercentValid("-14");

        assertionPercentValid("21");
        assertionPercentValid("-21");

        assertionPercentValid("22");
        assertionPercentValid("-22");

        assertionPercentValid("26");
        assertionPercentValid("-26");

        assertionPercentValid("32");
        assertionPercentValid("-32");

        assertionPercentValid("33");
        assertionPercentValid("-33");

        assertionPercentValid("38");
        assertionPercentValid("-38");

        assertionPercentValid("43");
        assertionPercentValid("-43");

        assertionPercentValid("44");
        assertionPercentValid("-44");

        assertionPercentValid("50");
        assertionPercentValid("-50");

        assertionPercentValid("54");
        assertionPercentValid("-54");

        assertionPercentValid("55");
        assertionPercentValid("-55");

        assertionPercentValid("62");
        assertionPercentValid("-62");

        assertionPercentValid("65");
        assertionPercentValid("-65");

        assertionPercentValid("66");
        assertionPercentValid("-66");

        assertionPercentValid("74");
        assertionPercentValid("-74");

        assertionPercentValid("76");
        assertionPercentValid("-76");

        assertionPercentValid("77");
        assertionPercentValid("-77");

        assertionPercentValid("86");
        assertionPercentValid("-86");

        assertionPercentValid("87");
        assertionPercentValid("-87");

        assertionPercentValid("88");
        assertionPercentValid("-88");

        assertionPercentValid("98");
        assertionPercentValid("-98");

        assertionPercentValid("99");
        assertionPercentValid("-99");

        assertionPercentValid("100");
        assertionPercentValid("-100");

        assertionPercentValid("108");
        assertionPercentValid("-108");

        assertionPercentValid("111");
        assertionPercentValid("-111");

        assertionPercentValid("129");
        assertionPercentValid("-129");

        assertionPercentValid("138");
        assertionPercentValid("-138");

        assertionPercentValid("145");
        assertionPercentValid("-145");

        assertionPercentValid("152");
        assertionPercentValid("-152");

        assertionPercentValid("161");
        assertionPercentValid("-161");

        assertionPercentValid("171");
        assertionPercentValid("-171");

        assertionPercentValid("185");
        assertionPercentValid("-185");

        assertionPercentValid("197");
        assertionPercentValid("-197");

        assertionPercentValid("210");
        assertionPercentValid("-210");

        assertionPercentValid("211");
        assertionPercentValid("-211");

        assertionPercentValid("214");
        assertionPercentValid("-214");

        assertionPercentValid("222");
        assertionPercentValid("-222");

        assertionPercentValid("226");
        assertionPercentValid("-226");

        assertionPercentValid("237");
        assertionPercentValid("-237");

        assertionPercentValid("244");
        assertionPercentValid("-244");

        assertionPercentValid("253");
        assertionPercentValid("-253");

        assertionPercentValid("260");
        assertionPercentValid("-260");

        assertionPercentValid("278");
        assertionPercentValid("-278");

        assertionPercentValid("286");
        assertionPercentValid("-286");

        assertionPercentValid("301");
        assertionPercentValid("-301");

        assertionPercentValid("314");
        assertionPercentValid("-314");

        assertionPercentValid("322");
        assertionPercentValid("-322");

        assertionPercentValid("328");
        assertionPercentValid("-328");

        assertionPercentValid("331");
        assertionPercentValid("-331");

        assertionPercentValid("333");
        assertionPercentValid("-333");

        assertionPercentValid("348");
        assertionPercentValid("-348");

        assertionPercentValid("356");
        assertionPercentValid("-356");

        assertionPercentValid("367");
        assertionPercentValid("-367");

        assertionPercentValid("376");
        assertionPercentValid("-376");

        assertionPercentValid("389");
        assertionPercentValid("-389");

        assertionPercentValid("403");
        assertionPercentValid("-403");

        assertionPercentValid("418");
        assertionPercentValid("-418");

        assertionPercentValid("428");
        assertionPercentValid("-428");

        assertionPercentValid("431");
        assertionPercentValid("-431");

        assertionPercentValid("433");
        assertionPercentValid("-433");

        assertionPercentValid("444");
        assertionPercentValid("-444");

        assertionPercentValid("449");
        assertionPercentValid("-449");

        assertionPercentValid("453");
        assertionPercentValid("-453");

        assertionPercentValid("467");
        assertionPercentValid("-467");

        assertionPercentValid("479");
        assertionPercentValid("-479");

        assertionPercentValid("492");
        assertionPercentValid("-492");

        assertionPercentValid("512");
        assertionPercentValid("-512");

        assertionPercentValid("524");
        assertionPercentValid("-524");

        assertionPercentValid("539");
        assertionPercentValid("-539");

        assertionPercentValid("544");
        assertionPercentValid("-544");

        assertionPercentValid("547");
        assertionPercentValid("-547");

        assertionPercentValid("553");
        assertionPercentValid("-553");

        assertionPercentValid("555");
        assertionPercentValid("-555");

        assertionPercentValid("562");
        assertionPercentValid("-562");

        assertionPercentValid("570");
        assertionPercentValid("-570");

        assertionPercentValid("574");
        assertionPercentValid("-574");

        assertionPercentValid("582");
        assertionPercentValid("-582");

        assertionPercentValid("586");
        assertionPercentValid("-586");

        assertionPercentValid("599");
        assertionPercentValid("-599");

        assertionPercentValid("612");
        assertionPercentValid("-612");

        assertionPercentValid("614");
        assertionPercentValid("-614");

        assertionPercentValid("628");
        assertionPercentValid("-628");

        assertionPercentValid("638");
        assertionPercentValid("-638");

        assertionPercentValid("642");
        assertionPercentValid("-642");

        assertionPercentValid("655");
        assertionPercentValid("-655");

        assertionPercentValid("657");
        assertionPercentValid("-657");

        assertionPercentValid("661");
        assertionPercentValid("-661");

        assertionPercentValid("666");
        assertionPercentValid("-666");

        assertionPercentValid("679");
        assertionPercentValid("-679");

        assertionPercentValid("683");
        assertionPercentValid("-683");

        assertionPercentValid("690");
        assertionPercentValid("-690");

        assertionPercentValid("704");
        assertionPercentValid("-704");

        assertionPercentValid("718");
        assertionPercentValid("-718");

        assertionPercentValid("721");
        assertionPercentValid("-721");

        assertionPercentValid("739");
        assertionPercentValid("-739");

        assertionPercentValid("742");
        assertionPercentValid("-742");

        assertionPercentValid("756");
        assertionPercentValid("-756");

        assertionPercentValid("763");
        assertionPercentValid("-763");

        assertionPercentValid("763");
        assertionPercentValid("-763");

        assertionPercentValid("766");
        assertionPercentValid("-766");

        assertionPercentValid("777");
        assertionPercentValid("-777");

        assertionPercentValid("777");
        assertionPercentValid("-777");

        assertionPercentValid("785");
        assertionPercentValid("-785");

        assertionPercentValid("812");
        assertionPercentValid("-812");

        assertionPercentValid("824");
        assertionPercentValid("-824");

        assertionPercentValid("831");
        assertionPercentValid("-831");

        assertionPercentValid("847");
        assertionPercentValid("-847");

        assertionPercentValid("856");
        assertionPercentValid("-856");

        assertionPercentValid("862");
        assertionPercentValid("-862");

        assertionPercentValid("877");
        assertionPercentValid("-877");

        assertionPercentValid("879");
        assertionPercentValid("-879");

        assertionPercentValid("882");
        assertionPercentValid("-882");

        assertionPercentValid("888");
        assertionPercentValid("-888");

        assertionPercentValid("893");
        assertionPercentValid("-893");

        assertionPercentValid("905");
        assertionPercentValid("-905");

        assertionPercentValid("914");
        assertionPercentValid("-914");

        assertionPercentValid("927");
        assertionPercentValid("-927");

        assertionPercentValid("935");
        assertionPercentValid("-935");

        assertionPercentValid("946");
        assertionPercentValid("-946");

        assertionPercentValid("952");
        assertionPercentValid("-952");

        assertionPercentValid("963");
        assertionPercentValid("-963");

        assertionPercentValid("977");
        assertionPercentValid("-977");

        assertionPercentValid("986");
        assertionPercentValid("-986");

        assertionPercentValid("988");
        assertionPercentValid("-988");

        assertionPercentValid("999");
        assertionPercentValid("-999");

        assertionPercentValid("1000");
        assertionPercentValid("-1000");

        assertionPercentValid("1008");
        assertionPercentValid("-1008");

        assertionPercentValid("1111");
        assertionPercentValid("-1111");

        assertionPercentValid("1114");
        assertionPercentValid("-1114");

        assertionPercentValid("1226");
        assertionPercentValid("-1226");

        assertionPercentValid("1314");
        assertionPercentValid("-1314");

        assertionPercentValid("1403");
        assertionPercentValid("-1403");

        assertionPercentValid("1524");
        assertionPercentValid("-1524");

        assertionPercentValid("1628");
        assertionPercentValid("-1628");

        assertionPercentValid("1718");
        assertionPercentValid("-1718");

        assertionPercentValid("1812");
        assertionPercentValid("-1812");

        assertionPercentValid("1905");
        assertionPercentValid("-1905");

        assertionPercentValid("2013");
        assertionPercentValid("-2013");

        assertionPercentValid("2175");
        assertionPercentValid("-2175");

        assertionPercentValid("2112");
        assertionPercentValid("-2112");

        assertionPercentValid("2222");
        assertionPercentValid("-2222");

        assertionPercentValid("2256");
        assertionPercentValid("-2256");

        assertionPercentValid("2367");
        assertionPercentValid("-2367");

        assertionPercentValid("2498");
        assertionPercentValid("-2498");

        assertionPercentValid("2596");
        assertionPercentValid("-2596");

        assertionPercentValid("2696");
        assertionPercentValid("-2696");

        assertionPercentValid("2731");
        assertionPercentValid("-2731");

        assertionPercentValid("2891");
        assertionPercentValid("-2891");

        assertionPercentValid("2964");
        assertionPercentValid("-2964");

        assertionPercentValid("3015");
        assertionPercentValid("-3015");

        assertionPercentValid("3173");
        assertionPercentValid("-3173");

        assertionPercentValid("3224");
        assertionPercentValid("-3224");

        assertionPercentValid("3265");
        assertionPercentValid("-3265");

        assertionPercentValid("3324");
        assertionPercentValid("-3324");

        assertionPercentValid("3333");
        assertionPercentValid("-3333");

        assertionPercentValid("3477");
        assertionPercentValid("-3477");

        assertionPercentValid("3531");
        assertionPercentValid("-3531");

        assertionPercentValid("3647");
        assertionPercentValid("-3647");

        assertionPercentValid("3787");
        assertionPercentValid("-3787");

        assertionPercentValid("3889");
        assertionPercentValid("-3889");

        assertionPercentValid("3932");
        assertionPercentValid("-3932");

        assertionPercentValid("4077");
        assertionPercentValid("-4077");

        assertionPercentValid("4178");
        assertionPercentValid("-4178");

        assertionPercentValid("4222");
        assertionPercentValid("-4222");

        assertionPercentValid("4336");
        assertionPercentValid("-4336");

        assertionPercentValid("4378");
        assertionPercentValid("-4378");

        assertionPercentValid("4425");
        assertionPercentValid("-4425");

        assertionPercentValid("4444");
        assertionPercentValid("-4444");

        assertionPercentValid("4554");
        assertionPercentValid("-4554");

        assertionPercentValid("4647");
        assertionPercentValid("-4647");

        assertionPercentValid("4781");
        assertionPercentValid("-4781");

        assertionPercentValid("4856");
        assertionPercentValid("-4856");

        assertionPercentValid("4986");
        assertionPercentValid("-4986");

        assertionPercentValid("5086");
        assertionPercentValid("-5086");

        assertionPercentValid("5154");
        assertionPercentValid("-5154");

        assertionPercentValid("5243");
        assertionPercentValid("-5243");

        assertionPercentValid("5323");
        assertionPercentValid("-5323");

        assertionPercentValid("5416");
        assertionPercentValid("-5416");

        assertionPercentValid("5448");
        assertionPercentValid("-5448");

        assertionPercentValid("5555");
        assertionPercentValid("-5555");

        assertionPercentValid("5570");
        assertionPercentValid("-5570");

        assertionPercentValid("5631");
        assertionPercentValid("-5631");

        assertionPercentValid("5960");
        assertionPercentValid("-5960");

        assertionPercentValid("6036");
        assertionPercentValid("-6036");

        assertionPercentValid("6271");
        assertionPercentValid("-6271");

        assertionPercentValid("6354");
        assertionPercentValid("-6354");

        assertionPercentValid("6429");
        assertionPercentValid("-6429");

        assertionPercentValid("6519");
        assertionPercentValid("-6519");

        assertionPercentValid("6560");
        assertionPercentValid("-6560");

        assertionPercentValid("6666");
        assertionPercentValid("-6666");

        assertionPercentValid("6667");
        assertionPercentValid("-6667");

        assertionPercentValid("6753");
        assertionPercentValid("-6753");

        assertionPercentValid("6874");
        assertionPercentValid("-6874");

        assertionPercentValid("6950");
        assertionPercentValid("-6950");

        assertionPercentValid("7071");
        assertionPercentValid("-7071");

        assertionPercentValid("7118");
        assertionPercentValid("-7118");

        assertionPercentValid("7224");
        assertionPercentValid("-7224");

        assertionPercentValid("7335");
        assertionPercentValid("-7335");

        assertionPercentValid("7452");
        assertionPercentValid("-7452");

        assertionPercentValid("7589");
        assertionPercentValid("-7589");

        assertionPercentValid("7672");
        assertionPercentValid("-7672");

        assertionPercentValid("7765");
        assertionPercentValid("-7765");

        assertionPercentValid("7777");
        assertionPercentValid("-7777");

        assertionPercentValid("7850");
        assertionPercentValid("-7850");

        assertionPercentValid("7919");
        assertionPercentValid("-7919");

        assertionPercentValid("8072");
        assertionPercentValid("-8072");

        assertionPercentValid("8146");
        assertionPercentValid("-8146");

        assertionPercentValid("8230");
        assertionPercentValid("-8230");

        assertionPercentValid("8336");
        assertionPercentValid("-8336");

        assertionPercentValid("8419");
        assertionPercentValid("-8419");

        assertionPercentValid("8591");
        assertionPercentValid("-8591");

        assertionPercentValid("8636");
        assertionPercentValid("-8636");

        assertionPercentValid("8745");
        assertionPercentValid("-8745");

        assertionPercentValid("8784");
        assertionPercentValid("-8784");

        assertionPercentValid("8855");
        assertionPercentValid("-8855");

        assertionPercentValid("8888");
        assertionPercentValid("-8888");

        assertionPercentValid("8958");
        assertionPercentValid("-8958");

        assertionPercentValid("9083");
        assertionPercentValid("-9083");

        assertionPercentValid("9112");
        assertionPercentValid("-9112");

        assertionPercentValid("9214");
        assertionPercentValid("-9214");

        assertionPercentValid("9335");
        assertionPercentValid("-9335");

        assertionPercentValid("9487");
        assertionPercentValid("-9487");

        assertionPercentValid("9522");
        assertionPercentValid("-9522");

        assertionPercentValid("9672");
        assertionPercentValid("-9672");

        assertionPercentValid("9755");
        assertionPercentValid("-9755");

        assertionPercentValid("9826");
        assertionPercentValid("-9826");

        assertionPercentValid("9989");
        assertionPercentValid("-9989");

        assertionPercentValid("9896");
        assertionPercentValid("-9896");

        assertionPercentValid("9999");
        assertionPercentValid("-9999");

        assertionPercentValid("55555");
        assertionPercentValid("-55555");

        assertionPercentValid("82534");
        assertionPercentValid("-82534");

        assertionPercentValid("437903");
        assertionPercentValid("-437903");

        assertionPercentValid("840736");
        assertionPercentValid("-840736");

        assertionPercentValid("2234567");
        assertionPercentValid("-2234567");

        assertionPercentValid("4534074");
        assertionPercentValid("-4534074");

        assertionPercentValid("49771169");
        assertionPercentValid("-49771169");

        assertionPercentValid("72324911");
        assertionPercentValid("-72324911");

        assertionPercentValid("659254843");
        assertionPercentValid("-659254843");

        assertionPercentValid("888888888");
        assertionPercentValid("-888888888");

        assertionPercentValid("1874764566");
        assertionPercentValid("-1874764566");

        assertionPercentValid("6243776665");
        assertionPercentValid("-6243776665");

        assertionPercentValid("47037036736");
        assertionPercentValid("-47037036736");

        assertionPercentValid("444444444444");
        assertionPercentValid("-444444444444");

        assertionPercentValid("999999999999");
        assertionPercentValid("-999999999999");

        assertionPercentValid("6578868631288");
        assertionPercentValid("-6578868631288");

        assertionPercentValid("8407407347404");
        assertionPercentValid("-8407407347404");

        assertionPercentValid("34691357824690");
        assertionPercentValid("-34691357824690");

        assertionPercentValid("96419752386415");
        assertionPercentValid("-96419752386415");

        assertionPercentValid("109975967636925");
        assertionPercentValid("-109975967636925");

        assertionPercentValid("565933996761014");
        assertionPercentValid("-565933996761014");

        assertionPercentValid("1082424349226241");
        assertionPercentValid("-1082424349226241");

        assertionPercentValid("10289622287797479");
        assertionPercentValid("-10289622287797479");

        assertionPercentValid("1E+17");
        assertionPercentValid("-1E+17");

        assertionPercentValid("5.647753E+23");
        assertionPercentValid("-5.647753E+23");

        assertionPercentValid("8.8788779E+24");
        assertionPercentValid("-8.8788779E+24");

        assertionPercentValid("9.86859867454E+38");
        assertionPercentValid("-9.86859867454E+38");

        assertionPercentValid("1E+296");
        assertionPercentValid("-1E+296");

        assertionPercentValid("5.647753E+549");
        assertionPercentValid("-5.647753E+549");

        assertionPercentValid("8.8788779E+897");
        assertionPercentValid("-8.8788779E+897");

        assertionPercentValid("9.86859867454E+978");
        assertionPercentValid("-9.86859867454E+978");

        assertionPercentValid("1E+1296");
        assertionPercentValid("-1E+1296");

        assertionPercentValid("5.647753E+1549");
        assertionPercentValid("-5.647753E+1549");

        assertionPercentValid("8.8788779E+1897");
        assertionPercentValid("-8.8788779E+1897");

        assertionPercentValid("9.86859867454E+1978");
        assertionPercentValid("-9.86859867454E+1978");

        assertionPercentValid("1E+2296");
        assertionPercentValid("-1E+2296");

        assertionPercentValid("5.647753E+2549");
        assertionPercentValid("-5.647753E+2549");

        assertionPercentValid("8.8788779E+2897");
        assertionPercentValid("-8.8788779E+2897");

        assertionPercentValid("9.86859867454E+2978");
        assertionPercentValid("-9.86859867454E+2978");

        assertionPercentValid("1E+3296");
        assertionPercentValid("-1E+3296");

        assertionPercentValid("5.647753E+3549");
        assertionPercentValid("-5.647753E+3549");

        assertionPercentValid("8.8788779E+3897");
        assertionPercentValid("-8.8788779E+3897");

        assertionPercentValid("9.86859867454E+3978");
        assertionPercentValid("-9.86859867454E+3978");

        assertionPercentValid("1E+4296");
        assertionPercentValid("-1E+4296");

        assertionPercentValid("5.647753E+4549");
        assertionPercentValid("-5.647753E+4549");

        assertionPercentValid("8.8788779E+4897");
        assertionPercentValid("-8.8788779E+4897");

        assertionPercentValid("9.86859867454E+4978");
        assertionPercentValid("-9.86859867454E+4978");

        assertionPercentValid("1E+5296");
        assertionPercentValid("-1E+5296");

        assertionPercentValid("5.647753E+5549");
        assertionPercentValid("-5.647753E+5549");

        assertionPercentValid("8.8788779E+5897");
        assertionPercentValid("-8.8788779E+5897");

        assertionPercentValid("9.86859867454E+5978");
        assertionPercentValid("-9.86859867454E+5978");

        assertionPercentValid("1E+6296");
        assertionPercentValid("-1E+6296");

        assertionPercentValid("5.647753E+6549");
        assertionPercentValid("-5.647753E+6549");

        assertionPercentValid("8.8788779E+6897");
        assertionPercentValid("-8.8788779E+6897");

        assertionPercentValid("9.86859867454E+6978");
        assertionPercentValid("-9.86859867454E+6978");

        assertionPercentValid("1E+7296");
        assertionPercentValid("-1E+7296");

        assertionPercentValid("5.647753E+7549");
        assertionPercentValid("-5.647753E+7549");

        assertionPercentValid("8.8788779E+7897");
        assertionPercentValid("-8.8788779E+7897");

        assertionPercentValid("9.86859867454E+7978");
        assertionPercentValid("-9.86859867454E+7978");

        assertionPercentValid("1E+8296");
        assertionPercentValid("-1E+8296");

        assertionPercentValid("5.647753E+8549");
        assertionPercentValid("-5.647753E+8549");

        assertionPercentValid("8.8788779E+8897");
        assertionPercentValid("-8.8788779E+8897");

        assertionPercentValid("9.86859867454E+8978");
        assertionPercentValid("-9.86859867454E+8978");

        assertionPercentValid("1E+9296");
        assertionPercentValid("-1E+9296");

        assertionPercentValid("5.647753E+9549");
        assertionPercentValid("-5.647753E+9549");

        assertionPercentValid("8.8788779E+9897");
        assertionPercentValid("-8.8788779E+9897");

        assertionPercentValid("9.86859867454E+9978");
        assertionPercentValid("-9.86859867454E+9978");

        assertionPercentValid("1E+10296");
        assertionPercentValid("-1E+10296");

        assertionPercentValid("5.647753E+10549");
        assertionPercentValid("-5.647753E+10549");

        assertionPercentValid("8.8788779E+10897");
        assertionPercentValid("-8.8788779E+10897");

        assertionPercentValid("9.86859867454E+10978");
        assertionPercentValid("-9.86859867454E+10978");

        assertionPercentValid("9999999999999999");
        assertionPercentValid("-9999999999999999");

        assertionPercentValid("9999999999999999E9999");
        assertionPercentValid("-9999999999999999E9999");

    }

    @Test
    void percentDecimal(){
        assertionPercentValid("0.002");
        assertionPercentValid("-0.002");

        assertionPercentValid("0.003");
        assertionPercentValid("-0.003");

        assertionPercentValid("0.004");
        assertionPercentValid("-0.004");

        assertionPercentValid("0.004");
        assertionPercentValid("-0.004");

        assertionPercentValid("0.005");
        assertionPercentValid("-0.005");

        assertionPercentValid("0.006");
        assertionPercentValid("-0.006");

        assertionPercentValid("0.007");
        assertionPercentValid("-0.007");

        assertionPercentValid("0.008");
        assertionPercentValid("-0.008");

        assertionPercentValid("0.009");
        assertionPercentValid("-0.009");

        assertionPercentValid("0.010");
        assertionPercentValid("-0.010");

        assertionPercentValid("0.00011");
        assertionPercentValid("-0.00011");

        assertionPercentValid("0.00043");
        assertionPercentValid("-0.00043");

        assertionPercentValid("0.00098");
        assertionPercentValid("-0.00098");

        assertionPercentValid("0.00152");
        assertionPercentValid("-0.00152");

        assertionPercentValid("0.00278");
        assertionPercentValid("-0.00278");

        assertionPercentValid("0.00367");
        assertionPercentValid("-0.00367");

        assertionPercentValid("0.00547");
        assertionPercentValid("-0.00547");

        assertionPercentValid("0.00562");
        assertionPercentValid("-0.00562");

        assertionPercentValid("0.00679");
        assertionPercentValid("-0.00679");

        assertionPercentValid("0.00742");
        assertionPercentValid("-0.00742");

        assertionPercentValid("0.00893");
        assertionPercentValid("-0.00893");

        assertionPercentValid("0.00935");
        assertionPercentValid("-0.00935");

        assertionPercentValid("0.00952");
        assertionPercentValid("-0.00952");

        assertionPercentValid("0.00999");
        assertionPercentValid("-0.00999");

        assertionPercentValid("0.000001008");
        assertionPercentValid("-0.000001008");

        assertionPercentValid("0.000002256");
        assertionPercentValid("-0.000002256");

        assertionPercentValid("0.000003173");
        assertionPercentValid("-0.000003173");

        assertionPercentValid("0.000003477");
        assertionPercentValid("-0.000003477");

        assertionPercentValid("0.000003889");
        assertionPercentValid("-0.000003889");

        assertionPercentValid("0.000004378");
        assertionPercentValid("-0.000004378");

        assertionPercentValid("0.000004647");
        assertionPercentValid("-0.000004647");

        assertionPercentValid("0.000005448");
        assertionPercentValid("-0.000005448");

        assertionPercentValid("0.000006271");
        assertionPercentValid("-0.000006271");

        assertionPercentValid("0.000007118");
        assertionPercentValid("-0.000007118");

        assertionPercentValid("0.000008072");
        assertionPercentValid("-0.000008072");

        assertionPercentValid("0.000009112");
        assertionPercentValid("-0.000009112");

        assertionPercentValid("0.000009214");
        assertionPercentValid("-0.000009214");

        assertionPercentValid("0.000055555");
        assertionPercentValid("-0.000055555");

        assertionPercentValid("0.000082534");
        assertionPercentValid("-0.000082534");

        assertionPercentValid("0.072324911");
        assertionPercentValid("-0.072324911");

        assertionPercentValid("0.888888888");
        assertionPercentValid("-0.888888888");

        assertionPercentValid("1E-17");
        assertionPercentValid("-1E-17");

        assertionPercentValid("5.647753E-11");
        assertionPercentValid("-5.647753E-11");

        assertionPercentValid("8.8788779E-10");
        assertionPercentValid("-8.8788779E-10");

        assertionPercentValid("9.86859867454E-16");
        assertionPercentValid("-9.86859867454E-16");

        assertionPercentValid("1E-296");
        assertionPercentValid("-1E-296");

        assertionPercentValid("5.647753E-537");
        assertionPercentValid("-5.647753E-537");

        assertionPercentValid("8.8788779E-883");
        assertionPercentValid("-8.8788779E-883");

        assertionPercentValid("9.86859867454E-956");
        assertionPercentValid("-9.86859867454E-956");

        assertionPercentValid("1E-1296");
        assertionPercentValid("-1E-1296");

        assertionPercentValid("5.647753E-1537");
        assertionPercentValid("-5.647753E-1537");

        assertionPercentValid("8.8788779E-1883");
        assertionPercentValid("-8.8788779E-1883");

        assertionPercentValid("9.86859867454E-1956");
        assertionPercentValid("-9.86859867454E-1956");

        assertionPercentValid("1E-2296");
        assertionPercentValid("-1E-2296");

        assertionPercentValid("5.647753E-2537");
        assertionPercentValid("-5.647753E-2537");

        assertionPercentValid("8.8788779E-2883");
        assertionPercentValid("-8.8788779E-2883");

        assertionPercentValid("9.86859867454E-2956");
        assertionPercentValid("-9.86859867454E-2956");

        assertionPercentValid("1E-3296");
        assertionPercentValid("-1E-3296");

        assertionPercentValid("5.647753E-3537");
        assertionPercentValid("-5.647753E-3537");

        assertionPercentValid("8.8788779E-3883");
        assertionPercentValid("-8.8788779E-3883");

        assertionPercentValid("9.86859867454E-3956");
        assertionPercentValid("-9.86859867454E-3956");

        assertionPercentValid("1E-4296");
        assertionPercentValid("-1E-4296");

        assertionPercentValid("5.647753E-4537");
        assertionPercentValid("-5.647753E-4537");

        assertionPercentValid("8.8788779E-4883");
        assertionPercentValid("-8.8788779E-4883");

        assertionPercentValid("9.86859867454E-4956");
        assertionPercentValid("-9.86859867454E-4956");

        assertionPercentValid("1E-5296");
        assertionPercentValid("-1E-5296");

        assertionPercentValid("5.647753E-5537");
        assertionPercentValid("-5.647753E-5537");

        assertionPercentValid("8.8788779E-5883");
        assertionPercentValid("-8.8788779E-5883");

        assertionPercentValid("9.86859867454E-5956");
        assertionPercentValid("-9.86859867454E-5956");

        assertionPercentValid("1E-6296");
        assertionPercentValid("-1E-6296");

        assertionPercentValid("5.647753E-6537");
        assertionPercentValid("-5.647753E-6537");

        assertionPercentValid("8.8788779E-6883");
        assertionPercentValid("-8.8788779E-6883");

        assertionPercentValid("9.86859867454E-6956");
        assertionPercentValid("-9.86859867454E-6956");

        assertionPercentValid("1E-7296");
        assertionPercentValid("-1E-7296");

        assertionPercentValid("5.647753E-7537");
        assertionPercentValid("-5.647753E-7537");

        assertionPercentValid("8.8788779E-7883");
        assertionPercentValid("-8.8788779E-7883");

        assertionPercentValid("9.86859867454E-7956");
        assertionPercentValid("-9.86859867454E-7956");

        assertionPercentValid("1E-8296");
        assertionPercentValid("-1E-8296");

        assertionPercentValid("5.647753E-8537");
        assertionPercentValid("-5.647753E-8537");

        assertionPercentValid("8.8788779E-8883");
        assertionPercentValid("-8.8788779E-8883");

        assertionPercentValid("9.86859867454E-8956");
        assertionPercentValid("-9.86859867454E-8956");

        assertionPercentValid("1E-9296");
        assertionPercentValid("-1E-9296");

        assertionPercentValid("5.647753E-9537");
        assertionPercentValid("-5.647753E-9537");

        assertionPercentValid("8.8788779E-9883");
        assertionPercentValid("-8.8788779E-9883");

        assertionPercentValid("9.86859867454E-9956");
        assertionPercentValid("-9.86859867454E-9956");

        assertionPercentValid("1E-10296");
        assertionPercentValid("-1E-10296");

        assertionPercentValid("5.647753E-10537");
        assertionPercentValid("-5.647753E-10537");

        assertionPercentValid("8.8788779E-10883");
        assertionPercentValid("-8.8788779E-10883");

        assertionPercentValid("9.86859867454E-10956");
        assertionPercentValid("-9.86859867454E-10956");

        assertionPercentValid("-1E-9999");
        assertionPercentValid("1E-9999");

        assertionPercentValid("-0.0000000000000001");
        assertionPercentValid("0.0000000000000001");
    }
    
    private void assertionPercentValid(String xString) {
        BigDecimal x = new BigDecimal(xString);
        BigDecimal resultExpected = BigDecimal.ZERO;

        unary.setNumber(x);
        unary.calculateUnary(OperationsEnum.PERCENT);
        BigDecimal resultActual = unary.getResult();

        assertEquals(resultExpected, resultActual);

        calculator.setNumberFirst(x);
        calculator.calculator(OperationsEnum.PERCENT);
        resultActual = calculator.getResult();
        assertEquals(resultExpected, resultActual);
        
        assertPercentInvalid();
    }

    private void assertPercentInvalid() {
        assertEnumNull();

        assertEnumInvalid(OperationsEnum.ADD);
        assertEnumInvalid(OperationsEnum.SUBTRACT);
        assertEnumInvalid(OperationsEnum.DIVIDE);
        assertEnumInvalid(OperationsEnum.MULTIPLY);
    }

    private void assertEnumInvalid(OperationsEnum operationsEnum) {
        try {
            unary.calculateUnary(operationsEnum);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals( "Enter unary operation", e.getMessage());
        }
    }

    private void assertEnumNull() {
        try {
            calculator.calculator(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("Enter operation", e.getMessage());
        }

    }
}
