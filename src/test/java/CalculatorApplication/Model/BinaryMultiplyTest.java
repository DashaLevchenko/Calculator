package CalculatorApplication.Model;

import CalculatorApplication.Model.Exceptions.DivideZeroException;
import CalculatorApplication.Model.Exceptions.InvalidInputException;
import CalculatorApplication.Model.Exceptions.ResultUndefinedException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class BinaryMultiplyTest {
    private Binary binary = new Binary();
    private ArrayList<Object> formula;

    @Test
    void multiplyInteger(){
        assertionMultiply("0", "1", "0");
        assertionMultiply("0", "-1", "0");
        assertionMultiply("1", "0", "0");
        assertionMultiply("-1", "0", "0");

        //both operands are integer
        assertionMultiply("1", "1", "1");
        assertionMultiply("1", "-1", "-1");
        assertionMultiply("-1", "1", "-1");
        assertionMultiply("-1", "-1", "1");

        assertionMultiply("1", "2", "2");
        assertionMultiply("1", "-2", "-2");
        assertionMultiply("-1", "2", "-2");
        assertionMultiply("-1", "-2", "2");

        assertionMultiply("6", "6", "36");
        assertionMultiply("6", "-6", "-36");
        assertionMultiply("-6", "6", "-36");
        assertionMultiply("-6", "-6", "36");

        assertionMultiply("54", "544", "29376");
        assertionMultiply("54", "-544", "-29376");
        assertionMultiply("-54", "544", "-29376");
        assertionMultiply("-54", "-544", "29376");

        assertionMultiply("77", "77", "5929");
        assertionMultiply("77", "-77", "-5929");
        assertionMultiply("-77", "77", "-5929");
        assertionMultiply("-77", "-77", "5929");

        assertionMultiply("195", "24764", "4828980");
        assertionMultiply("195", "-24764", "-4828980");
        assertionMultiply("-195", "24764", "-4828980");
        assertionMultiply("-195", "-24764", "4828980");

        assertionMultiply("356", "44", "15664");
        assertionMultiply("356", "-44", "-15664");
        assertionMultiply("-356", "44", "-15664");
        assertionMultiply("-356", "-44", "15664");

        assertionMultiply("492", "996", "490032");
        assertionMultiply("492", "-996", "-490032");
        assertionMultiply("-492", "996", "-490032");
        assertionMultiply("-492", "-996", "490032");

        assertionMultiply("628", "120", "75360");
        assertionMultiply("628", "-120", "-75360");
        assertionMultiply("-628", "120", "-75360");
        assertionMultiply("-628", "-120", "75360");

        assertionMultiply("1812", "66", "119592");
        assertionMultiply("1812", "-66", "-119592");
        assertionMultiply("-1812", "66", "-119592");
        assertionMultiply("-1812", "-66", "119592");

        assertionMultiply("2256", "87496874762", "197392949463072");
        assertionMultiply("2256", "-87496874762", "-197392949463072");
        assertionMultiply("-2256", "87496874762", "-197392949463072");
        assertionMultiply("-2256", "-87496874762", "197392949463072");

        assertionMultiply("4647", "74556387735", "346463533804545");
        assertionMultiply("4647", "-74556387735", "-346463533804545");
        assertionMultiply("-4647", "74556387735", "-346463533804545");
        assertionMultiply("-4647", "-74556387735", "346463533804545");

        assertionMultiply("5086", "309059", "1571874074");
        assertionMultiply("5086", "-309059", "-1571874074");
        assertionMultiply("-5086", "309059", "-1571874074");
        assertionMultiply("-5086", "-309059", "1571874074");

        assertionMultiply("9335", "2678", "24999130");
        assertionMultiply("9335", "-2678", "-24999130");
        assertionMultiply("-9335", "2678", "-24999130");
        assertionMultiply("-9335", "-2678", "24999130");

        assertionMultiply("49771169", "34", "1692219746");
        assertionMultiply("49771169", "-34", "-1692219746");
        assertionMultiply("-49771169", "34", "-1692219746");
        assertionMultiply("-49771169", "-34", "1692219746");

        assertionMultiply("578793", "74992", "43404844656");
        assertionMultiply("578793", "-74992", "-43404844656");
        assertionMultiply("-578793", "74992", "-43404844656");
        assertionMultiply("-578793", "-74992", "43404844656");

        assertionMultiply("4534074", "690", "3128511060");
        assertionMultiply("4534074", "-690", "-3128511060");
        assertionMultiply("-4534074", "690", "-3128511060");
        assertionMultiply("-4534074", "-690", "3128511060");

        assertionMultiply("895757683", "647580", "580074760357140");
        assertionMultiply("895757683", "-647580", "-580074760357140");
        assertionMultiply("-895757683", "647580", "-580074760357140");
        assertionMultiply("-895757683", "-647580", "580074760357140");

        assertionMultiply("6578868631288", "4565", "30032535301829720");
        assertionMultiply("6578868631288", "-4565", "-30032535301829720");
        assertionMultiply("-6578868631288", "4565", "-30032535301829720");
        assertionMultiply("-6578868631288", "-4565", "30032535301829720");

        assertionMultiply("8797976455423", "7287384905", "64114240815794975589815");
        assertionMultiply("8797976455423", "-7287384905", "-64114240815794975589815");
        assertionMultiply("-8797976455423", "7287384905", "-64114240815794975589815");
        assertionMultiply("-8797976455423", "-7287384905", "64114240815794975589815");

        assertionMultiply("839875982893745783", "42456265346", "35657997587469427886916535918");
        assertionMultiply("839875982893745783", "-42456265346", "-35657997587469427886916535918");
        assertionMultiply("-839875982893745783", "42456265346", "-35657997587469427886916535918");
        assertionMultiply("-839875982893745783", "-42456265346", "35657997587469427886916535918");

        //first operand is decimal
        assertionMultiply("0.1", "2", "0.2");
        assertionMultiply("0.1", "-2", "-0.2");
        assertionMultiply("-0.1", "2", "-0.2");
        assertionMultiply("-0.1", "-2", "0.2");

        assertionMultiply("0.001", "1", "0.001");
        assertionMultiply("0.001", "-1", "-0.001");
        assertionMultiply("-0.001", "1", "-0.001");
        assertionMultiply("-0.001", "-1", "0.001");

        assertionMultiply("0.002", "10", "0.020");
        assertionMultiply("0.002", "-10", "-0.020");
        assertionMultiply("-0.002", "10", "-0.020");
        assertionMultiply("-0.002", "-10", "0.020");

        assertionMultiply("0.003", "3", "0.009");
        assertionMultiply("0.003", "-3", "-0.009");
        assertionMultiply("-0.003", "3", "-0.009");
        assertionMultiply("-0.003", "-3", "0.009");

        assertionMultiply("0.004", "4", "0.016");
        assertionMultiply("0.004", "-4", "-0.016");
        assertionMultiply("-0.004", "4", "-0.016");
        assertionMultiply("-0.004", "-4", "0.016");

        assertionMultiply("0.005", "5", "0.025");
        assertionMultiply("0.005", "-5", "-0.025");
        assertionMultiply("-0.005", "5", "-0.025");
        assertionMultiply("-0.005", "-5", "0.025");

        assertionMultiply("0.006", "6", "0.036");
        assertionMultiply("0.006", "-6", "-0.036");
        assertionMultiply("-0.006", "6", "-0.036");
        assertionMultiply("-0.006", "-6", "0.036");

        assertionMultiply("0.007", "7", "0.049");
        assertionMultiply("0.007", "-7", "-0.049");
        assertionMultiply("-0.007", "7", "-0.049");
        assertionMultiply("-0.007", "-7", "0.049");

        assertionMultiply("0.008", "8", "0.064");
        assertionMultiply("0.008", "-8", "-0.064");
        assertionMultiply("-0.008", "8", "-0.064");
        assertionMultiply("-0.008", "-8", "0.064");

        assertionMultiply("0.009", "9", "0.081");
        assertionMultiply("0.009", "-9", "-0.081");
        assertionMultiply("-0.009", "9", "-0.081");
        assertionMultiply("-0.009", "-9", "0.081");

        assertionMultiply("0.010", "100", "1.000");
        assertionMultiply("0.010", "-100", "-1.000");
        assertionMultiply("-0.010", "100", "-1.000");
        assertionMultiply("-0.010", "-100", "1.000");

        assertionMultiply("0.00011", "11", "0.00121");
        assertionMultiply("0.00011", "-11", "-0.00121");
        assertionMultiply("-0.00011", "11", "-0.00121");
        assertionMultiply("-0.00011", "-11", "0.00121");

        assertionMultiply("0.00043", "433", "0.18619");
        assertionMultiply("0.00043", "-433", "-0.18619");
        assertionMultiply("-0.00043", "433", "-0.18619");
        assertionMultiply("-0.00043", "-433", "0.18619");

        assertionMultiply("0.00098", "994", "0.97412");
        assertionMultiply("0.00098", "-994", "-0.97412");
        assertionMultiply("-0.00098", "994", "-0.97412");
        assertionMultiply("-0.00098", "-994", "0.97412");

        assertionMultiply("0.00152", "77", "0.11704");
        assertionMultiply("0.00152", "-77", "-0.11704");
        assertionMultiply("-0.00152", "77", "-0.11704");
        assertionMultiply("-0.00152", "-77", "0.11704");

        assertionMultiply("0.00278", "8", "0.02224");
        assertionMultiply("0.00278", "-8", "-0.02224");
        assertionMultiply("-0.00278", "8", "-0.02224");
        assertionMultiply("-0.00278", "-8", "0.02224");

        assertionMultiply("0.00367", "89", "0.32663");
        assertionMultiply("0.00367", "-89", "-0.32663");
        assertionMultiply("-0.00367", "89", "-0.32663");
        assertionMultiply("-0.00367", "-89", "0.32663");

        assertionMultiply("0.00547", "21", "0.11487");
        assertionMultiply("0.00547", "-21", "-0.11487");
        assertionMultiply("-0.00547", "21", "-0.11487");
        assertionMultiply("-0.00547", "-21", "0.11487");

        assertionMultiply("0.00562", "52", "0.29224");
        assertionMultiply("0.00562", "-52", "-0.29224");
        assertionMultiply("-0.00562", "52", "-0.29224");
        assertionMultiply("-0.00562", "-52", "0.29224");

        assertionMultiply("0.00679", "511", "3.46969");
        assertionMultiply("0.00679", "-511", "-3.46969");
        assertionMultiply("-0.00679", "511", "-3.46969");
        assertionMultiply("-0.00679", "-511", "3.46969");

        assertionMultiply("0.00742", "56", "0.41552");
        assertionMultiply("0.00742", "-56", "-0.41552");
        assertionMultiply("-0.00742", "56", "-0.41552");
        assertionMultiply("-0.00742", "-56", "0.41552");

        assertionMultiply("0.00893", "4223", "37.71139");
        assertionMultiply("0.00893", "-4223", "-37.71139");
        assertionMultiply("-0.00893", "4223", "-37.71139");
        assertionMultiply("-0.00893", "-4223", "37.71139");

        assertionMultiply("0.00935", "563", "5.26405");
        assertionMultiply("0.00935", "-563", "-5.26405");
        assertionMultiply("-0.00935", "563", "-5.26405");
        assertionMultiply("-0.00935", "-563", "5.26405");

        assertionMultiply("0.00952", "96", "0.91392");
        assertionMultiply("0.00952", "-96", "-0.91392");
        assertionMultiply("-0.00952", "96", "-0.91392");
        assertionMultiply("-0.00952", "-96", "0.91392");

        assertionMultiply("0.00999", "999", "9.98001");
        assertionMultiply("0.00999", "-999", "-9.98001");
        assertionMultiply("-0.00999", "999", "-9.98001");
        assertionMultiply("-0.00999", "-999", "9.98001");

        assertionMultiply("0.000001008", "73", "0.000073584");
        assertionMultiply("0.000001008", "-73", "-0.000073584");
        assertionMultiply("-0.000001008", "73", "-0.000073584");
        assertionMultiply("-0.000001008", "-73", "0.000073584");

        assertionMultiply("0.000002256", "87496874762", "197392.949463072");
        assertionMultiply("0.000002256", "-87496874762", "-197392.949463072");
        assertionMultiply("-0.000002256", "87496874762", "-197392.949463072");
        assertionMultiply("-0.000002256", "-87496874762", "197392.949463072");

        assertionMultiply("0.000003173", "57", "0.000180861");
        assertionMultiply("0.000003173", "-57", "-0.000180861");
        assertionMultiply("-0.000003173", "57", "-0.000180861");
        assertionMultiply("-0.000003173", "-57", "0.000180861");

        assertionMultiply("0.000003477", "875", "0.003042375");
        assertionMultiply("0.000003477", "-875", "-0.003042375");
        assertionMultiply("-0.000003477", "875", "-0.003042375");
        assertionMultiply("-0.000003477", "-875", "0.003042375");

        assertionMultiply("0.000003889", "34", "0.000132226");
        assertionMultiply("0.000003889", "-34", "-0.000132226");
        assertionMultiply("-0.000003889", "34", "-0.000132226");
        assertionMultiply("-0.000003889", "-34", "0.000132226");

        assertionMultiply("0.000004378", "356653", "1.561426834");
        assertionMultiply("0.000004378", "-356653", "-1.561426834");
        assertionMultiply("-0.000004378", "356653", "-1.561426834");
        assertionMultiply("-0.000004378", "-356653", "1.561426834");

        assertionMultiply("0.000004647", "74556387735", "346463.533804545");
        assertionMultiply("0.000004647", "-74556387735", "-346463.533804545");
        assertionMultiply("-0.000004647", "74556387735", "-346463.533804545");
        assertionMultiply("-0.000004647", "-74556387735", "346463.533804545");

        assertionMultiply("0.000005448", "54448", "0.296632704");
        assertionMultiply("0.000005448", "-54448", "-0.296632704");
        assertionMultiply("-0.000005448", "54448", "-0.296632704");
        assertionMultiply("-0.000005448", "-54448", "0.296632704");

        assertionMultiply("0.000006271", "31", "0.000194401");
        assertionMultiply("0.000006271", "-31", "-0.000194401");
        assertionMultiply("-0.000006271", "31", "-0.000194401");
        assertionMultiply("-0.000006271", "-31", "0.000194401");

        assertionMultiply("0.000007118", "9186032", "65.386175776");
        assertionMultiply("0.000007118", "-9186032", "-65.386175776");
        assertionMultiply("-0.000007118", "9186032", "-65.386175776");
        assertionMultiply("-0.000007118", "-9186032", "65.386175776");

        assertionMultiply("0.000008072", "96787", "0.781264664");
        assertionMultiply("0.000008072", "-96787", "-0.781264664");
        assertionMultiply("-0.000008072", "96787", "-0.781264664");
        assertionMultiply("-0.000008072", "-96787", "0.781264664");

        assertionMultiply("0.000009112", "41", "0.000373592");
        assertionMultiply("0.000009112", "-41", "-0.000373592");
        assertionMultiply("-0.000009112", "41", "-0.000373592");
        assertionMultiply("-0.000009112", "-41", "0.000373592");

        assertionMultiply("0.000009214", "9733", "0.089679862");
        assertionMultiply("0.000009214", "-9733", "-0.089679862");
        assertionMultiply("-0.000009214", "9733", "-0.089679862");
        assertionMultiply("-0.000009214", "-9733", "0.089679862");

        assertionMultiply("0.000055555", "55555", "3.086358025");
        assertionMultiply("0.000055555", "-55555", "-3.086358025");
        assertionMultiply("-0.000055555", "55555", "-3.086358025");
        assertionMultiply("-0.000055555", "-55555", "3.086358025");

        assertionMultiply("0.000082534", "88", "0.007262992");
        assertionMultiply("0.000082534", "-88", "-0.007262992");
        assertionMultiply("-0.000082534", "88", "-0.007262992");
        assertionMultiply("-0.000082534", "-88", "0.007262992");

        assertionMultiply("0.072324911", "23443", "1695.512888573");
        assertionMultiply("0.072324911", "-23443", "-1695.512888573");
        assertionMultiply("-0.072324911", "23443", "-1695.512888573");
        assertionMultiply("-0.072324911", "-23443", "1695.512888573");

        assertionMultiply("0.888888888", "888888888", "790123455.209876544");
        assertionMultiply("0.888888888", "-888888888", "-790123455.209876544");
        assertionMultiply("-0.888888888", "888888888", "-790123455.209876544");
        assertionMultiply("-0.888888888", "-888888888", "790123455.209876544");

        assertionMultiply("6.243776665", "637", "3977.285735605");
        assertionMultiply("6.243776665", "-637", "-3977.285735605");
        assertionMultiply("-6.243776665", "637", "-3977.285735605");
        assertionMultiply("-6.243776665", "-637", "3977.285735605");

        assertionMultiply("999.999999999", "999999999999", "999999999998000.000000001");
        assertionMultiply("999.999999999", "-999999999999", "-999999999998000.000000001");
        assertionMultiply("-999.999999999", "999999999999", "-999999999998000.000000001");
        assertionMultiply("-999.999999999", "-999999999999", "999999999998000.000000001");

        assertionMultiply("8407.407347404", "84074073474070", "706844983052079857.675814280");
        assertionMultiply("8407.407347404", "-84074073474070", "-706844983052079857.675814280");
        assertionMultiply("-8407.407347404", "84074073474070", "-706844983052079857.675814280");
        assertionMultiply("-8407.407347404", "-84074073474070", "706844983052079857.675814280");

        assertionMultiply("96419.752386415", "964197523864192", "92967686502579860504.365751680");
        assertionMultiply("96419.752386415", "-964197523864192", "-92967686502579860504.365751680");
        assertionMultiply("-96419.752386415", "964197523864192", "-92967686502579860504.365751680");
        assertionMultiply("-96419.752386415", "-964197523864192", "92967686502579860504.365751680");

        assertionMultiply("10289622.287797479", "349675", "3598023673485.583469325");
        assertionMultiply("10289622.287797479", "-349675", "-3598023673485.583469325");
        assertionMultiply("-10289622.287797479", "349675", "-3598023673485.583469325");
        assertionMultiply("-10289622.287797479", "-349675", "3598023673485.583469325");

        assertionMultiply("1E-17", "8787676", "8.787676E-11");
        assertionMultiply("1E-17", "-8787676", "-8.787676E-11");
        assertionMultiply("-1E-17", "8787676", "-8.787676E-11");
        assertionMultiply("-1E-17", "-8787676", "8.787676E-11");

        assertionMultiply("5647753E-17", "6567687746", "0.37092678170534738");
        assertionMultiply("5647753E-17", "-6567687746", "-0.37092678170534738");
        assertionMultiply("-5647753E-17", "6567687746", "-0.37092678170534738");
        assertionMultiply("-5647753E-17", "-6567687746", "0.37092678170534738");

        assertionMultiply("1E-296", "8787676", "8.787676E-290");
        assertionMultiply("1E-296", "-8787676", "-8.787676E-290");
        assertionMultiply("-1E-296", "8787676", "-8.787676E-290");
        assertionMultiply("-1E-296", "-8787676", "8.787676E-290");

        assertionMultiply("5647753E-543", "6567687746", "3.7092678170534738E-527");
        assertionMultiply("5647753E-543", "-6567687746", "-3.7092678170534738E-527");
        assertionMultiply("-5647753E-543", "6567687746", "-3.7092678170534738E-527");
        assertionMultiply("-5647753E-543", "-6567687746", "3.7092678170534738E-527");

        assertionMultiply("1E-1296", "8787676", "8.787676E-1290");
        assertionMultiply("1E-1296", "-8787676", "-8.787676E-1290");
        assertionMultiply("-1E-1296", "8787676", "-8.787676E-1290");
        assertionMultiply("-1E-1296", "-8787676", "8.787676E-1290");

        assertionMultiply("5647753E-1543", "6567687746", "3.7092678170534738E-1527");
        assertionMultiply("5647753E-1543", "-6567687746", "-3.7092678170534738E-1527");
        assertionMultiply("-5647753E-1543", "6567687746", "-3.7092678170534738E-1527");
        assertionMultiply("-5647753E-1543", "-6567687746", "3.7092678170534738E-1527");

        assertionMultiply("1E-2296", "8787676", "8.787676E-2290");
        assertionMultiply("1E-2296", "-8787676", "-8.787676E-2290");
        assertionMultiply("-1E-2296", "8787676", "-8.787676E-2290");
        assertionMultiply("-1E-2296", "-8787676", "8.787676E-2290");

        assertionMultiply("5647753E-2543", "6567687746", "3.7092678170534738E-2527");
        assertionMultiply("5647753E-2543", "-6567687746", "-3.7092678170534738E-2527");
        assertionMultiply("-5647753E-2543", "6567687746", "-3.7092678170534738E-2527");
        assertionMultiply("-5647753E-2543", "-6567687746", "3.7092678170534738E-2527");

        assertionMultiply("1E-3296", "8787676", "8.787676E-3290");
        assertionMultiply("1E-3296", "-8787676", "-8.787676E-3290");
        assertionMultiply("-1E-3296", "8787676", "-8.787676E-3290");
        assertionMultiply("-1E-3296", "-8787676", "8.787676E-3290");

        assertionMultiply("5647753E-3543", "6567687746", "3.7092678170534738E-3527");
        assertionMultiply("5647753E-3543", "-6567687746", "-3.7092678170534738E-3527");
        assertionMultiply("-5647753E-3543", "6567687746", "-3.7092678170534738E-3527");
        assertionMultiply("-5647753E-3543", "-6567687746", "3.7092678170534738E-3527");

        assertionMultiply("1E-4296", "8787676", "8.787676E-4290");
        assertionMultiply("1E-4296", "-8787676", "-8.787676E-4290");
        assertionMultiply("-1E-4296", "8787676", "-8.787676E-4290");
        assertionMultiply("-1E-4296", "-8787676", "8.787676E-4290");

        assertionMultiply("5647753E-4543", "6567687746", "3.7092678170534738E-4527");
        assertionMultiply("5647753E-4543", "-6567687746", "-3.7092678170534738E-4527");
        assertionMultiply("-5647753E-4543", "6567687746", "-3.7092678170534738E-4527");
        assertionMultiply("-5647753E-4543", "-6567687746", "3.7092678170534738E-4527");

        assertionMultiply("1E-5296", "8787676", "8.787676E-5290");
        assertionMultiply("1E-5296", "-8787676", "-8.787676E-5290");
        assertionMultiply("-1E-5296", "8787676", "-8.787676E-5290");
        assertionMultiply("-1E-5296", "-8787676", "8.787676E-5290");

        assertionMultiply("5647753E-5543", "6567687746", "3.7092678170534738E-5527");
        assertionMultiply("5647753E-5543", "-6567687746", "-3.7092678170534738E-5527");
        assertionMultiply("-5647753E-5543", "6567687746", "-3.7092678170534738E-5527");
        assertionMultiply("-5647753E-5543", "-6567687746", "3.7092678170534738E-5527");

        assertionMultiply("1E-6296", "8787676", "8.787676E-6290");
        assertionMultiply("1E-6296", "-8787676", "-8.787676E-6290");
        assertionMultiply("-1E-6296", "8787676", "-8.787676E-6290");
        assertionMultiply("-1E-6296", "-8787676", "8.787676E-6290");

        assertionMultiply("5647753E-6543", "6567687746", "3.7092678170534738E-6527");
        assertionMultiply("5647753E-6543", "-6567687746", "-3.7092678170534738E-6527");
        assertionMultiply("-5647753E-6543", "6567687746", "-3.7092678170534738E-6527");
        assertionMultiply("-5647753E-6543", "-6567687746", "3.7092678170534738E-6527");

        assertionMultiply("1E-7296", "8787676", "8.787676E-7290");
        assertionMultiply("1E-7296", "-8787676", "-8.787676E-7290");
        assertionMultiply("-1E-7296", "8787676", "-8.787676E-7290");
        assertionMultiply("-1E-7296", "-8787676", "8.787676E-7290");

        assertionMultiply("5647753E-7543", "6567687746", "3.7092678170534738E-7527");
        assertionMultiply("5647753E-7543", "-6567687746", "-3.7092678170534738E-7527");
        assertionMultiply("-5647753E-7543", "6567687746", "-3.7092678170534738E-7527");
        assertionMultiply("-5647753E-7543", "-6567687746", "3.7092678170534738E-7527");

        assertionMultiply("1E-8296", "8787676", "8.787676E-8290");
        assertionMultiply("1E-8296", "-8787676", "-8.787676E-8290");
        assertionMultiply("-1E-8296", "8787676", "-8.787676E-8290");
        assertionMultiply("-1E-8296", "-8787676", "8.787676E-8290");

        assertionMultiply("5647753E-8543", "6567687746", "3.7092678170534738E-8527");
        assertionMultiply("5647753E-8543", "-6567687746", "-3.7092678170534738E-8527");
        assertionMultiply("-5647753E-8543", "6567687746", "-3.7092678170534738E-8527");
        assertionMultiply("-5647753E-8543", "-6567687746", "3.7092678170534738E-8527");

        assertionMultiply("1E-9296", "8787676", "8.787676E-9290");
        assertionMultiply("1E-9296", "-8787676", "-8.787676E-9290");
        assertionMultiply("-1E-9296", "8787676", "-8.787676E-9290");
        assertionMultiply("-1E-9296", "-8787676", "8.787676E-9290");

        assertionMultiply("5647753E-9543", "6567687746", "3.7092678170534738E-9527");
        assertionMultiply("5647753E-9543", "-6567687746", "-3.7092678170534738E-9527");
        assertionMultiply("-5647753E-9543", "6567687746", "-3.7092678170534738E-9527");
        assertionMultiply("-5647753E-9543", "-6567687746", "3.7092678170534738E-9527");

        assertionMultiply("1E-10296", "8787676", "8.787676E-10290");
        assertionMultiply("1E-10296", "-8787676", "-8.787676E-10290");
        assertionMultiply("-1E-10296", "8787676", "-8.787676E-10290");
        assertionMultiply("-1E-10296", "-8787676", "8.787676E-10290");

        assertionMultiply("5647753E-10543", "6567687746", "3.7092678170534738E-10527");
        assertionMultiply("5647753E-10543", "-6567687746", "-3.7092678170534738E-10527");
        assertionMultiply("-5647753E-10543", "6567687746", "-3.7092678170534738E-10527");
        assertionMultiply("-5647753E-10543", "-6567687746", "3.7092678170534738E-10527");

        //second operand is decimal
        assertionMultiply("1", "0.2", "0.2");
        assertionMultiply("1", "-0.2", "-0.2");
        assertionMultiply("-1", "0.2", "-0.2");
        assertionMultiply("-1", "-0.2", "0.2");

        assertionMultiply("2", "0.010", "0.020");
        assertionMultiply("2", "-0.010", "-0.020");
        assertionMultiply("-2", "0.010", "-0.020");
        assertionMultiply("-2", "-0.010", "0.020");

        assertionMultiply("3", "0.003", "0.009");
        assertionMultiply("3", "-0.003", "-0.009");
        assertionMultiply("-3", "0.003", "-0.009");
        assertionMultiply("-3", "-0.003", "0.009");

        assertionMultiply("4", "0.004", "0.016");
        assertionMultiply("4", "-0.004", "-0.016");
        assertionMultiply("-4", "0.004", "-0.016");
        assertionMultiply("-4", "-0.004", "0.016");

        assertionMultiply("5", "0.005", "0.025");
        assertionMultiply("5", "-0.005", "-0.025");
        assertionMultiply("-5", "0.005", "-0.025");
        assertionMultiply("-5", "-0.005", "0.025");

        assertionMultiply("6", "0.006", "0.036");
        assertionMultiply("6", "-0.006", "-0.036");
        assertionMultiply("-6", "0.006", "-0.036");
        assertionMultiply("-6", "-0.006", "0.036");

        assertionMultiply("7", "0.007", "0.049");
        assertionMultiply("7", "-0.007", "-0.049");
        assertionMultiply("-7", "0.007", "-0.049");
        assertionMultiply("-7", "-0.007", "0.049");

        assertionMultiply("8", "0.008", "0.064");
        assertionMultiply("8", "-0.008", "-0.064");
        assertionMultiply("-8", "0.008", "-0.064");
        assertionMultiply("-8", "-0.008", "0.064");

        assertionMultiply("9", "0.009", "0.081");
        assertionMultiply("9", "-0.009", "-0.081");
        assertionMultiply("-9", "0.009", "-0.081");
        assertionMultiply("-9", "-0.009", "0.081");

        assertionMultiply("10", "0.100", "1.000");
        assertionMultiply("10", "-0.100", "-1.000");
        assertionMultiply("-10", "0.100", "-1.000");
        assertionMultiply("-10", "-0.100", "1.000");

        assertionMultiply("11", "0.00011", "0.00121");
        assertionMultiply("11", "-0.00011", "-0.00121");
        assertionMultiply("-11", "0.00011", "-0.00121");
        assertionMultiply("-11", "-0.00011", "0.00121");

        assertionMultiply("43", "0.00433", "0.18619");
        assertionMultiply("43", "-0.00433", "-0.18619");
        assertionMultiply("-43", "0.00433", "-0.18619");
        assertionMultiply("-43", "-0.00433", "0.18619");

        assertionMultiply("98", "0.00994", "0.97412");
        assertionMultiply("98", "-0.00994", "-0.97412");
        assertionMultiply("-98", "0.00994", "-0.97412");
        assertionMultiply("-98", "-0.00994", "0.97412");

        assertionMultiply("152", "0.00077", "0.11704");
        assertionMultiply("152", "-0.00077", "-0.11704");
        assertionMultiply("-152", "0.00077", "-0.11704");
        assertionMultiply("-152", "-0.00077", "0.11704");

        assertionMultiply("278", "0.00008", "0.02224");
        assertionMultiply("278", "-0.00008", "-0.02224");
        assertionMultiply("-278", "0.00008", "-0.02224");
        assertionMultiply("-278", "-0.00008", "0.02224");

        assertionMultiply("367", "0.00089", "0.32663");
        assertionMultiply("367", "-0.00089", "-0.32663");
        assertionMultiply("-367", "0.00089", "-0.32663");
        assertionMultiply("-367", "-0.00089", "0.32663");

        assertionMultiply("547", "0.00021", "0.11487");
        assertionMultiply("547", "-0.00021", "-0.11487");
        assertionMultiply("-547", "0.00021", "-0.11487");
        assertionMultiply("-547", "-0.00021", "0.11487");

        assertionMultiply("562", "0.00052", "0.29224");
        assertionMultiply("562", "-0.00052", "-0.29224");
        assertionMultiply("-562", "0.00052", "-0.29224");
        assertionMultiply("-562", "-0.00052", "0.29224");

        assertionMultiply("679", "0.00511", "3.46969");
        assertionMultiply("679", "-0.00511", "-3.46969");
        assertionMultiply("-679", "0.00511", "-3.46969");
        assertionMultiply("-679", "-0.00511", "3.46969");

        assertionMultiply("742", "0.00056", "0.41552");
        assertionMultiply("742", "-0.00056", "-0.41552");
        assertionMultiply("-742", "0.00056", "-0.41552");
        assertionMultiply("-742", "-0.00056", "0.41552");

        assertionMultiply("893", "0.04223", "37.71139");
        assertionMultiply("893", "-0.04223", "-37.71139");
        assertionMultiply("-893", "0.04223", "-37.71139");
        assertionMultiply("-893", "-0.04223", "37.71139");

        assertionMultiply("935", "0.00563", "5.26405");
        assertionMultiply("935", "-0.00563", "-5.26405");
        assertionMultiply("-935", "0.00563", "-5.26405");
        assertionMultiply("-935", "-0.00563", "5.26405");

        assertionMultiply("952", "0.00096", "0.91392");
        assertionMultiply("952", "-0.00096", "-0.91392");
        assertionMultiply("-952", "0.00096", "-0.91392");
        assertionMultiply("-952", "-0.00096", "0.91392");

        assertionMultiply("999", "0.00999", "9.98001");
        assertionMultiply("999", "-0.00999", "-9.98001");
        assertionMultiply("-999", "0.00999", "-9.98001");
        assertionMultiply("-999", "-0.00999", "9.98001");

        assertionMultiply("1008", "7.3E-7", "0.00073584");
        assertionMultiply("1008", "-7.3E-7", "-0.00073584");
        assertionMultiply("-1008", "7.3E-7", "-0.00073584");
        assertionMultiply("-1008", "-7.3E-7", "0.00073584");

        assertionMultiply("2256", "874.96874762", "1973929.49463072");
        assertionMultiply("2256", "-874.96874762", "-1973929.49463072");
        assertionMultiply("-2256", "874.96874762", "-1973929.49463072");
        assertionMultiply("-2256", "-874.96874762", "1973929.49463072");

        assertionMultiply("3173", "5.7E-7", "0.00180861");
        assertionMultiply("3173", "-5.7E-7", "-0.00180861");
        assertionMultiply("-3173", "5.7E-7", "-0.00180861");
        assertionMultiply("-3173", "-5.7E-7", "0.00180861");

        assertionMultiply("3477", "0.00000875", "0.03042375");
        assertionMultiply("3477", "-0.00000875", "-0.03042375");
        assertionMultiply("-3477", "0.00000875", "-0.03042375");
        assertionMultiply("-3477", "-0.00000875", "0.03042375");

        assertionMultiply("3889", "3.4E-7", "0.00132226");
        assertionMultiply("3889", "-3.4E-7", "-0.00132226");
        assertionMultiply("-3889", "3.4E-7", "-0.00132226");
        assertionMultiply("-3889", "-3.4E-7", "0.00132226");

        assertionMultiply("4378", "0.00356653", "15.61426834");
        assertionMultiply("4378", "-0.00356653", "-15.61426834");
        assertionMultiply("-4378", "0.00356653", "-15.61426834");
        assertionMultiply("-4378", "-0.00356653", "15.61426834");

        assertionMultiply("4647", "745.56387735", "3464635.33804545");
        assertionMultiply("4647", "-745.56387735", "-3464635.33804545");
        assertionMultiply("-4647", "745.56387735", "-3464635.33804545");
        assertionMultiply("-4647", "-745.56387735", "3464635.33804545");

        assertionMultiply("5448", "0.00054448", "2.96632704");
        assertionMultiply("5448", "-0.00054448", "-2.96632704");
        assertionMultiply("-5448", "0.00054448", "-2.96632704");
        assertionMultiply("-5448", "-0.00054448", "2.96632704");

        assertionMultiply("6271", "3.1E-7", "0.00194401");
        assertionMultiply("6271", "-3.1E-7", "-0.00194401");
        assertionMultiply("-6271", "3.1E-7", "-0.00194401");
        assertionMultiply("-6271", "-3.1E-7", "0.00194401");

        assertionMultiply("7118", "0.09186032", "653.86175776");
        assertionMultiply("7118", "-0.09186032", "-653.86175776");
        assertionMultiply("-7118", "0.09186032", "-653.86175776");
        assertionMultiply("-7118", "-0.09186032", "653.86175776");

        assertionMultiply("8072", "0.00096787", "7.81264664");
        assertionMultiply("8072", "-0.00096787", "-7.81264664");
        assertionMultiply("-8072", "0.00096787", "-7.81264664");
        assertionMultiply("-8072", "-0.00096787", "7.81264664");

        assertionMultiply("9112", "4.1E-7", "0.00373592");
        assertionMultiply("9112", "-4.1E-7", "-0.00373592");
        assertionMultiply("-9112", "4.1E-7", "-0.00373592");
        assertionMultiply("-9112", "-4.1E-7", "0.00373592");

        assertionMultiply("9214", "0.00009733", "0.89679862");
        assertionMultiply("9214", "-0.00009733", "-0.89679862");
        assertionMultiply("-9214", "0.00009733", "-0.89679862");
        assertionMultiply("-9214", "-0.00009733", "0.89679862");

        assertionMultiply("55555", "0.00055555", "30.86358025");
        assertionMultiply("55555", "-0.00055555", "-30.86358025");
        assertionMultiply("-55555", "0.00055555", "-30.86358025");
        assertionMultiply("-55555", "-0.00055555", "30.86358025");

        assertionMultiply("82534", "8.8E-7", "0.07262992");
        assertionMultiply("82534", "-8.8E-7", "-0.07262992");
        assertionMultiply("-82534", "8.8E-7", "-0.07262992");
        assertionMultiply("-82534", "-8.8E-7", "0.07262992");

        assertionMultiply("72324911", "0.00023443", "16955.12888573");
        assertionMultiply("72324911", "-0.00023443", "-16955.12888573");
        assertionMultiply("-72324911", "0.00023443", "-16955.12888573");
        assertionMultiply("-72324911", "-0.00023443", "16955.12888573");

        assertionMultiply("888888888", "8.88888888", "7901234552.09876544");
        assertionMultiply("888888888", "-8.88888888", "-7901234552.09876544");
        assertionMultiply("-888888888", "8.88888888", "-7901234552.09876544");
        assertionMultiply("-888888888", "-8.88888888", "7901234552.09876544");

        assertionMultiply("6243776665", "0.00000637", "39772.85735605");
        assertionMultiply("6243776665", "-0.00000637", "-39772.85735605");
        assertionMultiply("-6243776665", "0.00000637", "-39772.85735605");
        assertionMultiply("-6243776665", "-0.00000637", "39772.85735605");

        assertionMultiply("999999999999", "9999.99999999", "9999999999980000.00000001");
        assertionMultiply("999999999999", "-9999.99999999", "-9999999999980000.00000001");
        assertionMultiply("-999999999999", "9999.99999999", "-9999999999980000.00000001");
        assertionMultiply("-999999999999", "-9999.99999999", "9999999999980000.00000001");

        assertionMultiply("8407407347404", "840740.73474070", "7068449830520798576.75814280");
        assertionMultiply("8407407347404", "-840740.73474070", "-7068449830520798576.75814280");
        assertionMultiply("-8407407347404", "840740.73474070", "-7068449830520798576.75814280");
        assertionMultiply("-8407407347404", "-840740.73474070", "7068449830520798576.75814280");

        assertionMultiply("96419752386415", "9641975.23864192", "929676865025798605043.65751680");
        assertionMultiply("96419752386415", "-9641975.23864192", "-929676865025798605043.65751680");
        assertionMultiply("-96419752386415", "9641975.23864192", "-929676865025798605043.65751680");
        assertionMultiply("-96419752386415", "-9641975.23864192", "929676865025798605043.65751680");

        assertionMultiply("10289622287797479", "0.00349675", "35980236734855.83469325");
        assertionMultiply("10289622287797479", "-0.00349675", "-35980236734855.83469325");
        assertionMultiply("-10289622287797479", "0.00349675", "-35980236734855.83469325");
        assertionMultiply("-10289622287797479", "-0.00349675", "35980236734855.83469325");

        //both operands are decimal
        assertionMultiply("0.1", "0.2", "0.02");
        assertionMultiply("0.1", "-0.2", "-0.02");
        assertionMultiply("-0.1", "0.2", "-0.02");
        assertionMultiply("-0.1", "-0.2", "0.02");

        assertionMultiply("0.002", "0.010", "0.000020");
        assertionMultiply("0.002", "-0.010", "-0.000020");
        assertionMultiply("-0.002", "0.010", "-0.000020");
        assertionMultiply("-0.002", "-0.010", "0.000020");

        assertionMultiply("0.003", "0.003", "0.000009");
        assertionMultiply("0.003", "-0.003", "-0.000009");
        assertionMultiply("-0.003", "0.003", "-0.000009");
        assertionMultiply("-0.003", "-0.003", "0.000009");

        assertionMultiply("0.004", "0.004", "0.000016");
        assertionMultiply("0.004", "-0.004", "-0.000016");
        assertionMultiply("-0.004", "0.004", "-0.000016");
        assertionMultiply("-0.004", "-0.004", "0.000016");

        assertionMultiply("0.005", "0.005", "0.000025");
        assertionMultiply("0.005", "-0.005", "-0.000025");
        assertionMultiply("-0.005", "0.005", "-0.000025");
        assertionMultiply("-0.005", "-0.005", "0.000025");

        assertionMultiply("0.006", "0.006", "0.000036");
        assertionMultiply("0.006", "-0.006", "-0.000036");
        assertionMultiply("-0.006", "0.006", "-0.000036");
        assertionMultiply("-0.006", "-0.006", "0.000036");

        assertionMultiply("0.007", "0.007", "0.000049");
        assertionMultiply("0.007", "-0.007", "-0.000049");
        assertionMultiply("-0.007", "0.007", "-0.000049");
        assertionMultiply("-0.007", "-0.007", "0.000049");

        assertionMultiply("0.008", "0.008", "0.000064");
        assertionMultiply("0.008", "-0.008", "-0.000064");
        assertionMultiply("-0.008", "0.008", "-0.000064");
        assertionMultiply("-0.008", "-0.008", "0.000064");

        assertionMultiply("0.009", "0.009", "0.000081");
        assertionMultiply("0.009", "-0.009", "-0.000081");
        assertionMultiply("-0.009", "0.009", "-0.000081");
        assertionMultiply("-0.009", "-0.009", "0.000081");

        assertionMultiply("0.010", "0.100", "0.001000");
        assertionMultiply("0.010", "-0.100", "-0.001000");
        assertionMultiply("-0.010", "0.100", "-0.001000");
        assertionMultiply("-0.010", "-0.100", "0.001000");

        assertionMultiply("0.00011", "0.00011", "1.21E-8");
        assertionMultiply("0.00011", "-0.00011", "-1.21E-8");
        assertionMultiply("-0.00011", "0.00011", "-1.21E-8");
        assertionMultiply("-0.00011", "-0.00011", "1.21E-8");

        assertionMultiply("0.00043", "0.00433", "0.0000018619");
        assertionMultiply("0.00043", "-0.00433", "-0.0000018619");
        assertionMultiply("-0.00043", "0.00433", "-0.0000018619");
        assertionMultiply("-0.00043", "-0.00433", "0.0000018619");

        assertionMultiply("0.00098", "0.00994", "0.0000097412");
        assertionMultiply("0.00098", "-0.00994", "-0.0000097412");
        assertionMultiply("-0.00098", "0.00994", "-0.0000097412");
        assertionMultiply("-0.00098", "-0.00994", "0.0000097412");

        assertionMultiply("0.00152", "0.00077", "0.0000011704");
        assertionMultiply("0.00152", "-0.00077", "-0.0000011704");
        assertionMultiply("-0.00152", "0.00077", "-0.0000011704");
        assertionMultiply("-0.00152", "-0.00077", "0.0000011704");

        assertionMultiply("0.00278", "0.00008", "2.224E-7");
        assertionMultiply("0.00278", "-0.00008", "-2.224E-7");
        assertionMultiply("-0.00278", "0.00008", "-2.224E-7");
        assertionMultiply("-0.00278", "-0.00008", "2.224E-7");

        assertionMultiply("0.00367", "0.00089", "0.0000032663");
        assertionMultiply("0.00367", "-0.00089", "-0.0000032663");
        assertionMultiply("-0.00367", "0.00089", "-0.0000032663");
        assertionMultiply("-0.00367", "-0.00089", "0.0000032663");

        assertionMultiply("0.00547", "0.00021", "0.0000011487");
        assertionMultiply("0.00547", "-0.00021", "-0.0000011487");
        assertionMultiply("-0.00547", "0.00021", "-0.0000011487");
        assertionMultiply("-0.00547", "-0.00021", "0.0000011487");

        assertionMultiply("0.00562", "0.00052", "0.0000029224");
        assertionMultiply("0.00562", "-0.00052", "-0.0000029224");
        assertionMultiply("-0.00562", "0.00052", "-0.0000029224");
        assertionMultiply("-0.00562", "-0.00052", "0.0000029224");

        assertionMultiply("0.00679", "0.00511", "0.0000346969");
        assertionMultiply("0.00679", "-0.00511", "-0.0000346969");
        assertionMultiply("-0.00679", "0.00511", "-0.0000346969");
        assertionMultiply("-0.00679", "-0.00511", "0.0000346969");

        assertionMultiply("0.00742", "0.00056", "0.0000041552");
        assertionMultiply("0.00742", "-0.00056", "-0.0000041552");
        assertionMultiply("-0.00742", "0.00056", "-0.0000041552");
        assertionMultiply("-0.00742", "-0.00056", "0.0000041552");

        assertionMultiply("0.00893", "0.04223", "0.0003771139");
        assertionMultiply("0.00893", "-0.04223", "-0.0003771139");
        assertionMultiply("-0.00893", "0.04223", "-0.0003771139");
        assertionMultiply("-0.00893", "-0.04223", "0.0003771139");

        assertionMultiply("0.00935", "0.00563", "0.0000526405");
        assertionMultiply("0.00935", "-0.00563", "-0.0000526405");
        assertionMultiply("-0.00935", "0.00563", "-0.0000526405");
        assertionMultiply("-0.00935", "-0.00563", "0.0000526405");

        assertionMultiply("0.00952", "0.00096", "0.0000091392");
        assertionMultiply("0.00952", "-0.00096", "-0.0000091392");
        assertionMultiply("-0.00952", "0.00096", "-0.0000091392");
        assertionMultiply("-0.00952", "-0.00096", "0.0000091392");

        assertionMultiply("0.00999", "0.00999", "0.0000998001");
        assertionMultiply("0.00999", "-0.00999", "-0.0000998001");
        assertionMultiply("-0.00999", "0.00999", "-0.0000998001");
        assertionMultiply("-0.00999", "-0.00999", "0.0000998001");

        assertionMultiply("10.08", "7.3E-8", "7.3584E-7");
        assertionMultiply("10.08", "-7.3E-8", "-7.3584E-7");
        assertionMultiply("-10.08", "7.3E-8", "-7.3584E-7");
        assertionMultiply("-10.08", "-7.3E-8", "7.3584E-7");

        assertionMultiply("22.56", "87.496874762", "1973.92949463072");
        assertionMultiply("22.56", "-87.496874762", "-1973.92949463072");
        assertionMultiply("-22.56", "87.496874762", "-1973.92949463072");
        assertionMultiply("-22.56", "-87.496874762", "1973.92949463072");

        assertionMultiply("31.73", "5.7E-8", "0.00000180861");
        assertionMultiply("31.73", "-5.7E-8", "-0.00000180861");
        assertionMultiply("-31.73", "5.7E-8", "-0.00000180861");
        assertionMultiply("-31.73", "-5.7E-8", "0.00000180861");

        assertionMultiply("34.77", "8.75E-7", "0.00003042375");
        assertionMultiply("34.77", "-8.75E-7", "-0.00003042375");
        assertionMultiply("-34.77", "8.75E-7", "-0.00003042375");
        assertionMultiply("-34.77", "-8.75E-7", "0.00003042375");

        assertionMultiply("38.89", "3.4E-8", "0.00000132226");
        assertionMultiply("38.89", "-3.4E-8", "-0.00000132226");
        assertionMultiply("-38.89", "3.4E-8", "-0.00000132226");
        assertionMultiply("-38.89", "-3.4E-8", "0.00000132226");

        assertionMultiply("43.78", "0.000356653", "0.01561426834");
        assertionMultiply("43.78", "-0.000356653", "-0.01561426834");
        assertionMultiply("-43.78", "0.000356653", "-0.01561426834");
        assertionMultiply("-43.78", "-0.000356653", "0.01561426834");

        assertionMultiply("46.47", "74.556387735", "3464.63533804545");
        assertionMultiply("46.47", "-74.556387735", "-3464.63533804545");
        assertionMultiply("-46.47", "74.556387735", "-3464.63533804545");
        assertionMultiply("-46.47", "-74.556387735", "3464.63533804545");

        assertionMultiply("54.48", "0.000054448", "0.00296632704");
        assertionMultiply("54.48", "-0.000054448", "-0.00296632704");
        assertionMultiply("-54.48", "0.000054448", "-0.00296632704");
        assertionMultiply("-54.48", "-0.000054448", "0.00296632704");

        assertionMultiply("62.71", "3.1E-8", "0.00000194401");
        assertionMultiply("62.71", "-3.1E-8", "-0.00000194401");
        assertionMultiply("-62.71", "3.1E-8", "-0.00000194401");
        assertionMultiply("-62.71", "-3.1E-8", "0.00000194401");

        assertionMultiply("71.18", "0.009186032", "0.65386175776");
        assertionMultiply("71.18", "-0.009186032", "-0.65386175776");
        assertionMultiply("-71.18", "0.009186032", "-0.65386175776");
        assertionMultiply("-71.18", "-0.009186032", "0.65386175776");

        assertionMultiply("80.72", "0.000096787", "0.00781264664");
        assertionMultiply("80.72", "-0.000096787", "-0.00781264664");
        assertionMultiply("-80.72", "0.000096787", "-0.00781264664");
        assertionMultiply("-80.72", "-0.000096787", "0.00781264664");

        assertionMultiply("91.12", "4.1E-8", "0.00000373592");
        assertionMultiply("91.12", "-4.1E-8", "-0.00000373592");
        assertionMultiply("-91.12", "4.1E-8", "-0.00000373592");
        assertionMultiply("-91.12", "-4.1E-8", "0.00000373592");

        assertionMultiply("92.14", "0.000009733", "0.00089679862");
        assertionMultiply("92.14", "-0.000009733", "-0.00089679862");
        assertionMultiply("-92.14", "0.000009733", "-0.00089679862");
        assertionMultiply("-92.14", "-0.000009733", "0.00089679862");

        assertionMultiply("555.55", "0.000055555", "0.03086358025");
        assertionMultiply("555.55", "-0.000055555", "-0.03086358025");
        assertionMultiply("-555.55", "0.000055555", "-0.03086358025");
        assertionMultiply("-555.55", "-0.000055555", "0.03086358025");

        assertionMultiply("825.34", "8.8E-8", "0.00007262992");
        assertionMultiply("825.34", "-8.8E-8", "-0.00007262992");
        assertionMultiply("-825.34", "8.8E-8", "-0.00007262992");
        assertionMultiply("-825.34", "-8.8E-8", "0.00007262992");

        assertionMultiply("723249.11", "0.000023443", "16.95512888573");
        assertionMultiply("723249.11", "-0.000023443", "-16.95512888573");
        assertionMultiply("-723249.11", "0.000023443", "-16.95512888573");
        assertionMultiply("-723249.11", "-0.000023443", "16.95512888573");

        assertionMultiply("8888888.88", "0.888888888", "7901234.55209876544");
        assertionMultiply("8888888.88", "-0.888888888", "-7901234.55209876544");
        assertionMultiply("-8888888.88", "0.888888888", "-7901234.55209876544");
        assertionMultiply("-8888888.88", "-0.888888888", "7901234.55209876544");

        assertionMultiply("62437766.65", "6.37E-7", "39.77285735605");
        assertionMultiply("62437766.65", "-6.37E-7", "-39.77285735605");
        assertionMultiply("-62437766.65", "6.37E-7", "-39.77285735605");
        assertionMultiply("-62437766.65", "-6.37E-7", "39.77285735605");

        assertionMultiply("9999999999.99", "999.999999999", "9999999999980.00000000001");
        assertionMultiply("9999999999.99", "-999.999999999", "-9999999999980.00000000001");
        assertionMultiply("-9999999999.99", "999.999999999", "-9999999999980.00000000001");
        assertionMultiply("-9999999999.99", "-999.999999999", "9999999999980.00000000001");

        assertionMultiply("84074073474.04", "84074.073474070", "7068449830520798.57675814280");
        assertionMultiply("84074073474.04", "-84074.073474070", "-7068449830520798.57675814280");
        assertionMultiply("-84074073474.04", "84074.073474070", "-7068449830520798.57675814280");
        assertionMultiply("-84074073474.04", "-84074.073474070", "7068449830520798.57675814280");

        assertionMultiply("964197523864.15", "964197.523864192", "929676865025798605.04365751680");
        assertionMultiply("964197523864.15", "-964197.523864192", "-929676865025798605.04365751680");
        assertionMultiply("-964197523864.15", "964197.523864192", "-929676865025798605.04365751680");
        assertionMultiply("-964197523864.15", "-964197.523864192", "929676865025798605.04365751680");

        assertionMultiply("102896222877974.79", "0.000349675", "35980236734.85583469325");
        assertionMultiply("102896222877974.79", "-0.000349675", "-35980236734.85583469325");
        assertionMultiply("-102896222877974.79", "0.000349675", "-35980236734.85583469325");
        assertionMultiply("-102896222877974.79", "-0.000349675", "35980236734.85583469325");

        assertionMultiply("88788779E-17", "755634E-85", "6.7091820230886E-89");
        assertionMultiply("88788779E-17", "-755634E-85", "-6.7091820230886E-89");
        assertionMultiply("-88788779E-17", "755634E-85", "-6.7091820230886E-89");
        assertionMultiply("-88788779E-17", "-755634E-85", "6.7091820230886E-89");

        assertionMultiply("986859867454E-27", "536556E-643", "5.29505583041648424E-653");
        assertionMultiply("986859867454E-27", "-536556E-643", "-5.29505583041648424E-653");
        assertionMultiply("-986859867454E-27", "536556E-643", "-5.29505583041648424E-653");
        assertionMultiply("-986859867454E-27", "-536556E-643", "5.29505583041648424E-653");

        assertionMultiply("88788779E-890", "755634E-85", "6.7091820230886E-962");
        assertionMultiply("88788779E-890", "-755634E-85", "-6.7091820230886E-962");
        assertionMultiply("-88788779E-890", "755634E-85", "-6.7091820230886E-962");
        assertionMultiply("-88788779E-890", "-755634E-85", "6.7091820230886E-962");

        assertionMultiply("986859867454E-967", "536556E-643", "5.29505583041648424E-1593");
        assertionMultiply("986859867454E-967", "-536556E-643", "-5.29505583041648424E-1593");
        assertionMultiply("-986859867454E-967", "536556E-643", "-5.29505583041648424E-1593");
        assertionMultiply("-986859867454E-967", "-536556E-643", "5.29505583041648424E-1593");

        assertionMultiply("88788779E-1890", "755634E-85", "6.7091820230886E-1962");
        assertionMultiply("88788779E-1890", "-755634E-85", "-6.7091820230886E-1962");
        assertionMultiply("-88788779E-1890", "755634E-85", "-6.7091820230886E-1962");
        assertionMultiply("-88788779E-1890", "-755634E-85", "6.7091820230886E-1962");

        assertionMultiply("986859867454E-1967", "536556E-643", "5.29505583041648424E-2593");
        assertionMultiply("986859867454E-1967", "-536556E-643", "-5.29505583041648424E-2593");
        assertionMultiply("-986859867454E-1967", "536556E-643", "-5.29505583041648424E-2593");
        assertionMultiply("-986859867454E-1967", "-536556E-643", "5.29505583041648424E-2593");

        assertionMultiply("88788779E-2890", "755634E-85", "6.7091820230886E-2962");
        assertionMultiply("88788779E-2890", "-755634E-85", "-6.7091820230886E-2962");
        assertionMultiply("-88788779E-2890", "755634E-85", "-6.7091820230886E-2962");
        assertionMultiply("-88788779E-2890", "-755634E-85", "6.7091820230886E-2962");

        assertionMultiply("986859867454E-2967", "536556E-643", "5.29505583041648424E-3593");
        assertionMultiply("986859867454E-2967", "-536556E-643", "-5.29505583041648424E-3593");
        assertionMultiply("-986859867454E-2967", "536556E-643", "-5.29505583041648424E-3593");
        assertionMultiply("-986859867454E-2967", "-536556E-643", "5.29505583041648424E-3593");

        assertionMultiply("88788779E-3890", "755634E-85", "6.7091820230886E-3962");
        assertionMultiply("88788779E-3890", "-755634E-85", "-6.7091820230886E-3962");
        assertionMultiply("-88788779E-3890", "755634E-85", "-6.7091820230886E-3962");
        assertionMultiply("-88788779E-3890", "-755634E-85", "6.7091820230886E-3962");

        assertionMultiply("986859867454E-3967", "536556E-643", "5.29505583041648424E-4593");
        assertionMultiply("986859867454E-3967", "-536556E-643", "-5.29505583041648424E-4593");
        assertionMultiply("-986859867454E-3967", "536556E-643", "-5.29505583041648424E-4593");
        assertionMultiply("-986859867454E-3967", "-536556E-643", "5.29505583041648424E-4593");

        assertionMultiply("88788779E-4890", "755634E-85", "6.7091820230886E-4962");
        assertionMultiply("88788779E-4890", "-755634E-85", "-6.7091820230886E-4962");
        assertionMultiply("-88788779E-4890", "755634E-85", "-6.7091820230886E-4962");
        assertionMultiply("-88788779E-4890", "-755634E-85", "6.7091820230886E-4962");

        assertionMultiply("986859867454E-4967", "536556E-643", "5.29505583041648424E-5593");
        assertionMultiply("986859867454E-4967", "-536556E-643", "-5.29505583041648424E-5593");
        assertionMultiply("-986859867454E-4967", "536556E-643", "-5.29505583041648424E-5593");
        assertionMultiply("-986859867454E-4967", "-536556E-643", "5.29505583041648424E-5593");

        assertionMultiply("88788779E-5890", "755634E-85", "6.7091820230886E-5962");
        assertionMultiply("88788779E-5890", "-755634E-85", "-6.7091820230886E-5962");
        assertionMultiply("-88788779E-5890", "755634E-85", "-6.7091820230886E-5962");
        assertionMultiply("-88788779E-5890", "-755634E-85", "6.7091820230886E-5962");

        assertionMultiply("986859867454E-5967", "536556E-643", "5.29505583041648424E-6593");
        assertionMultiply("986859867454E-5967", "-536556E-643", "-5.29505583041648424E-6593");
        assertionMultiply("-986859867454E-5967", "536556E-643", "-5.29505583041648424E-6593");
        assertionMultiply("-986859867454E-5967", "-536556E-643", "5.29505583041648424E-6593");

        assertionMultiply("88788779E-6890", "755634E-85", "6.7091820230886E-6962");
        assertionMultiply("88788779E-6890", "-755634E-85", "-6.7091820230886E-6962");
        assertionMultiply("-88788779E-6890", "755634E-85", "-6.7091820230886E-6962");
        assertionMultiply("-88788779E-6890", "-755634E-85", "6.7091820230886E-6962");

        assertionMultiply("986859867454E-6967", "536556E-643", "5.29505583041648424E-7593");
        assertionMultiply("986859867454E-6967", "-536556E-643", "-5.29505583041648424E-7593");
        assertionMultiply("-986859867454E-6967", "536556E-643", "-5.29505583041648424E-7593");
        assertionMultiply("-986859867454E-6967", "-536556E-643", "5.29505583041648424E-7593");

        assertionMultiply("88788779E-7890", "755634E-85", "6.7091820230886E-7962");
        assertionMultiply("88788779E-7890", "-755634E-85", "-6.7091820230886E-7962");
        assertionMultiply("-88788779E-7890", "755634E-85", "-6.7091820230886E-7962");
        assertionMultiply("-88788779E-7890", "-755634E-85", "6.7091820230886E-7962");

        assertionMultiply("986859867454E-7967", "536556E-643", "5.29505583041648424E-8593");
        assertionMultiply("986859867454E-7967", "-536556E-643", "-5.29505583041648424E-8593");
        assertionMultiply("-986859867454E-7967", "536556E-643", "-5.29505583041648424E-8593");
        assertionMultiply("-986859867454E-7967", "-536556E-643", "5.29505583041648424E-8593");

        assertionMultiply("88788779E-8890", "755634E-85", "6.7091820230886E-8962");
        assertionMultiply("88788779E-8890", "-755634E-85", "-6.7091820230886E-8962");
        assertionMultiply("-88788779E-8890", "755634E-85", "-6.7091820230886E-8962");
        assertionMultiply("-88788779E-8890", "-755634E-85", "6.7091820230886E-8962");

        assertionMultiply("986859867454E-8967", "536556E-643", "5.29505583041648424E-9593");
        assertionMultiply("986859867454E-8967", "-536556E-643", "-5.29505583041648424E-9593");
        assertionMultiply("-986859867454E-8967", "536556E-643", "-5.29505583041648424E-9593");
        assertionMultiply("-986859867454E-8967", "-536556E-643", "5.29505583041648424E-9593");

        assertionMultiply("88788779E-9890", "755634E-85", "6.7091820230886E-9962");
        assertionMultiply("88788779E-9890", "-755634E-85", "-6.7091820230886E-9962");
        assertionMultiply("-88788779E-9890", "755634E-85", "-6.7091820230886E-9962");
        assertionMultiply("-88788779E-9890", "-755634E-85", "6.7091820230886E-9962");

        assertionMultiply("986859867454E-9967", "536556E-643", "5.29505583041648424E-10593");
        assertionMultiply("986859867454E-9967", "-536556E-643", "-5.29505583041648424E-10593");
        assertionMultiply("-986859867454E-9967", "536556E-643", "-5.29505583041648424E-10593");
        assertionMultiply("-986859867454E-9967", "-536556E-643", "5.29505583041648424E-10593");

        assertionMultiply("88788779E-10890", "755634E-85", "6.7091820230886E-10962");
        assertionMultiply("88788779E-10890", "-755634E-85", "-6.7091820230886E-10962");
        assertionMultiply("-88788779E-10890", "755634E-85", "-6.7091820230886E-10962");
        assertionMultiply("-88788779E-10890", "-755634E-85", "6.7091820230886E-10962");

        assertionMultiply("986859867454E-10967", "536556E-643", "5.29505583041648424E-11593");
        assertionMultiply("986859867454E-10967", "-536556E-643", "-5.29505583041648424E-11593");
        assertionMultiply("-986859867454E-10967", "536556E-643", "-5.29505583041648424E-11593");
        assertionMultiply("-986859867454E-10967", "-536556E-643", "5.29505583041648424E-11593");

        //Special numbers for realization
        //=> 9999999999999999
        assertionMultiply("9999999999999999", "0", "0");
        assertionMultiply("-9999999999999999", "0", "0");
        assertionMultiply("0", "9999999999999999", "0");
        assertionMultiply("0", "-9999999999999999", "0");

        assertionMultiply("9999999999999999", "1", "9999999999999999");
        assertionMultiply("9999999999999999", "-1", "-9999999999999999");
        assertionMultiply("-9999999999999999", "1", "-9999999999999999");
        assertionMultiply("-9999999999999999", "-1", "9999999999999999");

        assertionMultiply("9999999999999999", "9999999999999999", "99999999999999980000000000000001");
        assertionMultiply("9999999999999999", "-9999999999999999", "-99999999999999980000000000000001");
        assertionMultiply("-9999999999999999", "9999999999999999", "-99999999999999980000000000000001");
        assertionMultiply("-9999999999999999", "-9999999999999999", "99999999999999980000000000000001");

        assertionMultiply("9999999999999999", "0.0000000000000001", "0.9999999999999999");
        assertionMultiply("9999999999999999", "-0.0000000000000001", "-0.9999999999999999");
        assertionMultiply("-9999999999999999", "0.0000000000000001", "-0.9999999999999999");
        assertionMultiply("-9999999999999999", "-0.0000000000000001", "0.9999999999999999");

        assertionMultiply("9999999999999999", "9999999999999999E9999", "9.9999999999999980000000000000001E+10030");
        assertionMultiply("9999999999999999", "-9999999999999999E9999", "-9.9999999999999980000000000000001E+10030");
        assertionMultiply("-9999999999999999", "9999999999999999E9999", "-9.9999999999999980000000000000001E+10030");
        assertionMultiply("-9999999999999999", "-9999999999999999E9999", "9.9999999999999980000000000000001E+10030");

        assertionMultiply("9999999999999999", "1E-9999", "9.999999999999999E-9984");
        assertionMultiply("9999999999999999", "-1E-9999", "-9.999999999999999E-9984");
        assertionMultiply("-9999999999999999", "1E-9999", "-9.999999999999999E-9984");
        assertionMultiply("-9999999999999999", "-1E-9999", "9.999999999999999E-9984");

        //=>0.0000000000000001
        assertionMultiply("0.0000000000000001", "1", "1E-16");
        assertionMultiply("0.0000000000000001", "-1", "-1E-16");
        assertionMultiply("-0.0000000000000001", "1", "-1E-16");
        assertionMultiply("-0.0000000000000001", "-1", "1E-16");

        assertionMultiply("0.0000000000000001", "0.0000000000000001", "1E-32");
        assertionMultiply("0.0000000000000001", "-0.0000000000000001", "-1E-32");
        assertionMultiply("-0.0000000000000001", "0.0000000000000001", "-1E-32");
        assertionMultiply("-0.0000000000000001", "-0.0000000000000001", "1E-32");

        assertionMultiply("0.0000000000000001", "9999999999999999", "0.9999999999999999");
        assertionMultiply("0.0000000000000001", "-9999999999999999", "-0.9999999999999999");
        assertionMultiply("-0.0000000000000001", "9999999999999999", "-0.9999999999999999");
        assertionMultiply("-0.0000000000000001", "-9999999999999999", "0.9999999999999999");

        assertionMultiply("0.0000000000000001", "9999999999999999E9999", "9.999999999999999E+9998");
        assertionMultiply("0.0000000000000001", "-9999999999999999E9999", "-9.999999999999999E+9998");
        assertionMultiply("-0.0000000000000001", "9999999999999999E9999", "-9.999999999999999E+9998");
        assertionMultiply("-0.0000000000000001", "-9999999999999999E9999", "9.999999999999999E+9998");

        assertionMultiply("0.0000000000000001", "1E-9999", "1E-10015");
        assertionMultiply("0.0000000000000001", "-1E-9999", "-1E-10015");
        assertionMultiply("-0.0000000000000001", "1E-9999", "-1E-10015");
        assertionMultiply("-0.0000000000000001", "-1E-9999", "1E-10015");

        //=> 9999999999999999E9999
        assertionMultiply("9999999999999999E9999", "1", "9.999999999999999E+10014");
        assertionMultiply("9999999999999999E9999", "-1", "-9.999999999999999E+10014");
        assertionMultiply("-9999999999999999E9999", "1", "-9.999999999999999E+10014");
        assertionMultiply("-9999999999999999E9999", "-1", "9.999999999999999E+10014");

        assertionMultiply("9999999999999999E9999", "9999999999999999E9999", "9.9999999999999980000000000000001E+20029");
        assertionMultiply("9999999999999999E9999", "-9999999999999999E9999", "-9.9999999999999980000000000000001E+20029");
        assertionMultiply("-9999999999999999E9999", "9999999999999999E9999", "-9.9999999999999980000000000000001E+20029");
        assertionMultiply("-9999999999999999E9999", "-9999999999999999E9999", "9.9999999999999980000000000000001E+20029");

        assertionMultiply("9999999999999999E9999", "9999999999999999", "9.9999999999999980000000000000001E+10030");
        assertionMultiply("9999999999999999E9999", "-9999999999999999", "-9.9999999999999980000000000000001E+10030");
        assertionMultiply("-9999999999999999E9999", "9999999999999999", "-9.9999999999999980000000000000001E+10030");
        assertionMultiply("-9999999999999999E9999", "-9999999999999999", "9.9999999999999980000000000000001E+10030");

        assertionMultiply("9999999999999999E9999", "0.0000000000000001", "9.999999999999999E+9998");
        assertionMultiply("9999999999999999E9999", "-0.0000000000000001", "-9.999999999999999E+9998");
        assertionMultiply("-9999999999999999E9999", "0.0000000000000001", "-9.999999999999999E+9998");
        assertionMultiply("-9999999999999999E9999", "-0.0000000000000001", "9.999999999999999E+9998");

        assertionMultiply("9999999999999999E9999", "1E-9999", "9999999999999999");
        assertionMultiply("9999999999999999E9999", "-1E-9999", "-9999999999999999");
        assertionMultiply("-9999999999999999E9999", "1E-9999", "-9999999999999999");
        assertionMultiply("-9999999999999999E9999", "-1E-9999", "9999999999999999");

        //=> 1E-9999
        assertionMultiply("1E-9999", "1", "1E-9999");
        assertionMultiply("1E-9999", "-1", "-1E-9999");
        assertionMultiply("-1E-9999", "1", "-1E-9999");
        assertionMultiply("-1E-9999", "-1", "1E-9999");

        assertionMultiply("1E-9999", "1E-9999", "1E-19998");
        assertionMultiply("1E-9999", "-1E-9999", "-1E-19998");
        assertionMultiply("-1E-9999", "1E-9999", "-1E-19998");
        assertionMultiply("-1E-9999", "-1E-9999", "1E-19998");

        assertionMultiply("1E-9999", "9999999999999999", "9.999999999999999E-9984");
        assertionMultiply("1E-9999", "-9999999999999999", "-9.999999999999999E-9984");
        assertionMultiply("-1E-9999", "9999999999999999", "-9.999999999999999E-9984");
        assertionMultiply("-1E-9999", "-9999999999999999", "9.999999999999999E-9984");

        assertionMultiply("1E-9999", "0.0000000000000001", "1E-10015");
        assertionMultiply("1E-9999", "-0.0000000000000001", "-1E-10015");
        assertionMultiply("-1E-9999", "0.0000000000000001", "-1E-10015");
        assertionMultiply("-1E-9999", "-0.0000000000000001", "1E-10015");

        assertionMultiply("1E-9999", "9999999999999999E9999", "9999999999999999");
        assertionMultiply("1E-9999", "-9999999999999999E9999", "-9999999999999999");
        assertionMultiply("-1E-9999", "9999999999999999E9999", "-9999999999999999");
        assertionMultiply("-1E-9999", "-9999999999999999E9999", "9999999999999999");
    }



    void assertionMultiply(String xString, String yString, String resultString) {
        BigDecimal x = new BigDecimal(xString);
        BigDecimal y = new BigDecimal(yString);
        BigDecimal resultExpected = new BigDecimal(resultString);
        formula = new ArrayList<>(Arrays.asList(x, OperationsEnum.MULTIPLY, y, OperationsEnum.EQUAL));

        try {
            BigDecimal resultActual = Calculator.calculator(formula);
            assertEquals(resultExpected, resultActual);
        } catch (DivideZeroException | ResultUndefinedException | InvalidInputException e) {
            e.printStackTrace();
        }

        checkBinary(x, y, resultExpected);


        assertMultiplyInvalid(x, y);
    }

    private void checkBinary (BigDecimal x, BigDecimal y, BigDecimal resultExpected) {
        binary.setNumberFirst(x);
        binary.setNumberSecond(y);
        binary.setOperation(OperationsEnum.MULTIPLY);
        try {
            binary.calculateBinary();
        } catch (ResultUndefinedException | DivideZeroException e) {
            e.printStackTrace();
        }
        BigDecimal resultActual = binary.getResult();
        assertEquals(resultExpected, resultActual);

        assertEquals(x.multiply(y, MathContext.DECIMAL128), resultActual);
    }

    private void assertMultiplyInvalid(BigDecimal x, BigDecimal y) {
        assertEnumNull(x, y);

        assertEnumInvalid(OperationsEnum.SQR);
        assertEnumInvalid(OperationsEnum.SQRT);
        assertEnumInvalid(OperationsEnum.ONE_DIVIDE_X);
    }

    private void assertEnumInvalid(OperationsEnum operationsEnum) {
        try {
            binary.setOperation(operationsEnum);
            binary.calculateBinary();
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Enter binary operation");
        }
    }

    private void assertEnumNull (BigDecimal x, BigDecimal y) {
        formula = new ArrayList<>(Arrays.asList(x, null, y, OperationsEnum.EQUAL));
        try {
            Calculator.calculator(formula);
            fail();
        } catch (Exception e) {
            assertEquals("Can not calculate null operation, enter operation or number.", e.getMessage());
        }
    }


}