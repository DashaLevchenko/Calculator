package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryPercentTest {
    private Binary binary = new Binary();
    private ArrayList<Object> formula;
    @Test
    void percentInteger () {
        assertionsPercent("0", "1", "0.00");
        assertionsPercent("0", "-1", "0.00");
        assertionsPercent("1", "0", "0");
        assertionsPercent("-1", "0", "0");

        //both operands are integer
        assertionsPercent("1", "1", "0.01");
        assertionsPercent("1", "-1", "-0.01");
        assertionsPercent("-1", "1", "-0.01");
        assertionsPercent("-1", "-1", "0.01");

        assertionsPercent("1", "2", "0.02");
        assertionsPercent("1", "-2", "-0.02");
        assertionsPercent("-1", "2", "-0.02");
        assertionsPercent("-1", "-2", "0.02");

        assertionsPercent("6", "6", "0.36");
        assertionsPercent("6", "-6", "-0.36");
        assertionsPercent("-6", "6", "-0.36");
        assertionsPercent("-6", "-6", "0.36");

        assertionsPercent("54", "544", "293.76");
        assertionsPercent("54", "-544", "-293.76");
        assertionsPercent("-54", "544", "-293.76");
        assertionsPercent("-54", "-544", "293.76");

        assertionsPercent("77", "77", "59.29");
        assertionsPercent("77", "-77", "-59.29");
        assertionsPercent("-77", "77", "-59.29");
        assertionsPercent("-77", "-77", "59.29");

        assertionsPercent("195", "24764", "48289.80");
        assertionsPercent("195", "-24764", "-48289.80");
        assertionsPercent("-195", "24764", "-48289.80");
        assertionsPercent("-195", "-24764", "48289.80");

        assertionsPercent("356", "44", "156.64");
        assertionsPercent("356", "-44", "-156.64");
        assertionsPercent("-356", "44", "-156.64");
        assertionsPercent("-356", "-44", "156.64");

        assertionsPercent("492", "996", "4900.32");
        assertionsPercent("492", "-996", "-4900.32");
        assertionsPercent("-492", "996", "-4900.32");
        assertionsPercent("-492", "-996", "4900.32");

        assertionsPercent("628", "120", "753.6");
        assertionsPercent("628", "-120", "-753.6");
        assertionsPercent("-628", "120", "-753.6");
        assertionsPercent("-628", "-120", "753.6");

        assertionsPercent("1812", "66", "1195.92");
        assertionsPercent("1812", "-66", "-1195.92");
        assertionsPercent("-1812", "66", "-1195.92");
        assertionsPercent("-1812", "-66", "1195.92");

        assertionsPercent("2256", "87496874762", "1973929494630.72");
        assertionsPercent("2256", "-87496874762", "-1973929494630.72");
        assertionsPercent("-2256", "87496874762", "-1973929494630.72");
        assertionsPercent("-2256", "-87496874762", "1973929494630.72");

        assertionsPercent("4647", "74556387735", "3464635338045.45");
        assertionsPercent("4647", "-74556387735", "-3464635338045.45");
        assertionsPercent("-4647", "74556387735", "-3464635338045.45");
        assertionsPercent("-4647", "-74556387735", "3464635338045.45");

        assertionsPercent("5086", "309059", "15718740.74");
        assertionsPercent("5086", "-309059", "-15718740.74");
        assertionsPercent("-5086", "309059", "-15718740.74");
        assertionsPercent("-5086", "-309059", "15718740.74");

        assertionsPercent("9335", "2678", "249991.30");
        assertionsPercent("9335", "-2678", "-249991.30");
        assertionsPercent("-9335", "2678", "-249991.30");
        assertionsPercent("-9335", "-2678", "249991.30");

        assertionsPercent("49771169", "34", "16922197.46");
        assertionsPercent("49771169", "-34", "-16922197.46");
        assertionsPercent("-49771169", "34", "-16922197.46");
        assertionsPercent("-49771169", "-34", "16922197.46");

        assertionsPercent("578793", "74992", "434048446.56");
        assertionsPercent("578793", "-74992", "-434048446.56");
        assertionsPercent("-578793", "74992", "-434048446.56");
        assertionsPercent("-578793", "-74992", "434048446.56");

        assertionsPercent("4534074", "690", "31285110.6");
        assertionsPercent("4534074", "-690", "-31285110.6");
        assertionsPercent("-4534074", "690", "-31285110.6");
        assertionsPercent("-4534074", "-690", "31285110.6");

        assertionsPercent("895757683", "647580", "5800747603571.4");
        assertionsPercent("895757683", "-647580", "-5800747603571.4");
        assertionsPercent("-895757683", "647580", "-5800747603571.4");
        assertionsPercent("-895757683", "-647580", "5800747603571.4");

        assertionsPercent("6578868631288", "4565", "300325353018297.20");
        assertionsPercent("6578868631288", "-4565", "-300325353018297.20");
        assertionsPercent("-6578868631288", "4565", "-300325353018297.20");
        assertionsPercent("-6578868631288", "-4565", "300325353018297.20");

        assertionsPercent("8797976455423", "7287384905", "641142408157949755898.15");
        assertionsPercent("8797976455423", "-7287384905", "-641142408157949755898.15");
        assertionsPercent("-8797976455423", "7287384905", "-641142408157949755898.15");
        assertionsPercent("-8797976455423", "-7287384905", "641142408157949755898.15");

        assertionsPercent("839875982893745783", "42456265346", "356579975874694278869165359.18");
        assertionsPercent("839875982893745783", "-42456265346", "-356579975874694278869165359.18");
        assertionsPercent("-839875982893745783", "42456265346", "-356579975874694278869165359.18");
        assertionsPercent("-839875982893745783", "-42456265346", "356579975874694278869165359.18");

        //first operand is decimal
        assertionsPercent("0.1", "2", "0.002");
        assertionsPercent("0.1", "-2", "-0.002");
        assertionsPercent("-0.1", "2", "-0.002");
        assertionsPercent("-0.1", "-2", "0.002");

        assertionsPercent("0.001", "1", "0.00001");
        assertionsPercent("0.001", "-1", "-0.00001");
        assertionsPercent("-0.001", "1", "-0.00001");
        assertionsPercent("-0.001", "-1", "0.00001");

        assertionsPercent("0.002", "10", "0.0002");
        assertionsPercent("0.002", "-10", "-0.0002");
        assertionsPercent("-0.002", "10", "-0.0002");
        assertionsPercent("-0.002", "-10", "0.0002");

        assertionsPercent("0.003", "3", "0.00009");
        assertionsPercent("0.003", "-3", "-0.00009");
        assertionsPercent("-0.003", "3", "-0.00009");
        assertionsPercent("-0.003", "-3", "0.00009");

        assertionsPercent("0.004", "4", "0.00016");
        assertionsPercent("0.004", "-4", "-0.00016");
        assertionsPercent("-0.004", "4", "-0.00016");
        assertionsPercent("-0.004", "-4", "0.00016");

        assertionsPercent("0.005", "5", "0.00025");
        assertionsPercent("0.005", "-5", "-0.00025");
        assertionsPercent("-0.005", "5", "-0.00025");
        assertionsPercent("-0.005", "-5", "0.00025");

        assertionsPercent("0.006", "6", "0.00036");
        assertionsPercent("0.006", "-6", "-0.00036");
        assertionsPercent("-0.006", "6", "-0.00036");
        assertionsPercent("-0.006", "-6", "0.00036");

        assertionsPercent("0.007", "7", "0.00049");
        assertionsPercent("0.007", "-7", "-0.00049");
        assertionsPercent("-0.007", "7", "-0.00049");
        assertionsPercent("-0.007", "-7", "0.00049");

        assertionsPercent("0.008", "8", "0.00064");
        assertionsPercent("0.008", "-8", "-0.00064");
        assertionsPercent("-0.008", "8", "-0.00064");
        assertionsPercent("-0.008", "-8", "0.00064");

        assertionsPercent("0.009", "9", "0.00081");
        assertionsPercent("0.009", "-9", "-0.00081");
        assertionsPercent("-0.009", "9", "-0.00081");
        assertionsPercent("-0.009", "-9", "0.00081");

        assertionsPercent("0.010", "100", "0.010");
        assertionsPercent("0.010", "-100", "-0.010");
        assertionsPercent("-0.010", "100", "-0.010");
        assertionsPercent("-0.010", "-100", "0.010");

        assertionsPercent("0.00011", "11", "0.0000121");
        assertionsPercent("0.00011", "-11", "-0.0000121");
        assertionsPercent("-0.00011", "11", "-0.0000121");
        assertionsPercent("-0.00011", "-11", "0.0000121");

        assertionsPercent("0.00043", "433", "0.0018619");
        assertionsPercent("0.00043", "-433", "-0.0018619");
        assertionsPercent("-0.00043", "433", "-0.0018619");
        assertionsPercent("-0.00043", "-433", "0.0018619");

        assertionsPercent("0.00098", "994", "0.0097412");
        assertionsPercent("0.00098", "-994", "-0.0097412");
        assertionsPercent("-0.00098", "994", "-0.0097412");
        assertionsPercent("-0.00098", "-994", "0.0097412");

        assertionsPercent("0.00152", "77", "0.0011704");
        assertionsPercent("0.00152", "-77", "-0.0011704");
        assertionsPercent("-0.00152", "77", "-0.0011704");
        assertionsPercent("-0.00152", "-77", "0.0011704");

        assertionsPercent("0.00278", "8", "0.0002224");
        assertionsPercent("0.00278", "-8", "-0.0002224");
        assertionsPercent("-0.00278", "8", "-0.0002224");
        assertionsPercent("-0.00278", "-8", "0.0002224");

        assertionsPercent("0.00367", "89", "0.0032663");
        assertionsPercent("0.00367", "-89", "-0.0032663");
        assertionsPercent("-0.00367", "89", "-0.0032663");
        assertionsPercent("-0.00367", "-89", "0.0032663");

        assertionsPercent("0.00547", "21", "0.0011487");
        assertionsPercent("0.00547", "-21", "-0.0011487");
        assertionsPercent("-0.00547", "21", "-0.0011487");
        assertionsPercent("-0.00547", "-21", "0.0011487");

        assertionsPercent("0.00562", "52", "0.0029224");
        assertionsPercent("0.00562", "-52", "-0.0029224");
        assertionsPercent("-0.00562", "52", "-0.0029224");
        assertionsPercent("-0.00562", "-52", "0.0029224");

        assertionsPercent("0.00679", "511", "0.0346969");
        assertionsPercent("0.00679", "-511", "-0.0346969");
        assertionsPercent("-0.00679", "511", "-0.0346969");
        assertionsPercent("-0.00679", "-511", "0.0346969");

        assertionsPercent("0.00742", "56", "0.0041552");
        assertionsPercent("0.00742", "-56", "-0.0041552");
        assertionsPercent("-0.00742", "56", "-0.0041552");
        assertionsPercent("-0.00742", "-56", "0.0041552");

        assertionsPercent("0.00893", "4223", "0.3771139");
        assertionsPercent("0.00893", "-4223", "-0.3771139");
        assertionsPercent("-0.00893", "4223", "-0.3771139");
        assertionsPercent("-0.00893", "-4223", "0.3771139");

        assertionsPercent("0.00935", "563", "0.0526405");
        assertionsPercent("0.00935", "-563", "-0.0526405");
        assertionsPercent("-0.00935", "563", "-0.0526405");
        assertionsPercent("-0.00935", "-563", "0.0526405");

        assertionsPercent("0.00952", "96", "0.0091392");
        assertionsPercent("0.00952", "-96", "-0.0091392");
        assertionsPercent("-0.00952", "96", "-0.0091392");
        assertionsPercent("-0.00952", "-96", "0.0091392");

        assertionsPercent("0.00999", "999", "0.0998001");
        assertionsPercent("0.00999", "-999", "-0.0998001");
        assertionsPercent("-0.00999", "999", "-0.0998001");
        assertionsPercent("-0.00999", "-999", "0.0998001");

        assertionsPercent("0.000001008", "73", "7.3584E-7");
        assertionsPercent("0.000001008", "-73", "-7.3584E-7");
        assertionsPercent("-0.000001008", "73", "-7.3584E-7");
        assertionsPercent("-0.000001008", "-73", "7.3584E-7");

        assertionsPercent("0.000002256", "87496874762", "1973.92949463072");
        assertionsPercent("0.000002256", "-87496874762", "-1973.92949463072");
        assertionsPercent("-0.000002256", "87496874762", "-1973.92949463072");
        assertionsPercent("-0.000002256", "-87496874762", "1973.92949463072");

        assertionsPercent("0.000003173", "57", "0.00000180861");
        assertionsPercent("0.000003173", "-57", "-0.00000180861");
        assertionsPercent("-0.000003173", "57", "-0.00000180861");
        assertionsPercent("-0.000003173", "-57", "0.00000180861");

        assertionsPercent("0.000003477", "875", "0.00003042375");
        assertionsPercent("0.000003477", "-875", "-0.00003042375");
        assertionsPercent("-0.000003477", "875", "-0.00003042375");
        assertionsPercent("-0.000003477", "-875", "0.00003042375");

        assertionsPercent("0.000003889", "34", "0.00000132226");
        assertionsPercent("0.000003889", "-34", "-0.00000132226");
        assertionsPercent("-0.000003889", "34", "-0.00000132226");
        assertionsPercent("-0.000003889", "-34", "0.00000132226");

        assertionsPercent("0.000004378", "356653", "0.01561426834");
        assertionsPercent("0.000004378", "-356653", "-0.01561426834");
        assertionsPercent("-0.000004378", "356653", "-0.01561426834");
        assertionsPercent("-0.000004378", "-356653", "0.01561426834");

        assertionsPercent("0.000004647", "74556387735", "3464.63533804545");
        assertionsPercent("0.000004647", "-74556387735", "-3464.63533804545");
        assertionsPercent("-0.000004647", "74556387735", "-3464.63533804545");
        assertionsPercent("-0.000004647", "-74556387735", "3464.63533804545");

        assertionsPercent("0.000005448", "54448", "0.00296632704");
        assertionsPercent("0.000005448", "-54448", "-0.00296632704");
        assertionsPercent("-0.000005448", "54448", "-0.00296632704");
        assertionsPercent("-0.000005448", "-54448", "0.00296632704");

        assertionsPercent("0.000006271", "31", "0.00000194401");
        assertionsPercent("0.000006271", "-31", "-0.00000194401");
        assertionsPercent("-0.000006271", "31", "-0.00000194401");
        assertionsPercent("-0.000006271", "-31", "0.00000194401");

        assertionsPercent("0.000007118", "9186032", "0.65386175776");
        assertionsPercent("0.000007118", "-9186032", "-0.65386175776");
        assertionsPercent("-0.000007118", "9186032", "-0.65386175776");
        assertionsPercent("-0.000007118", "-9186032", "0.65386175776");

        assertionsPercent("0.000008072", "96787", "0.00781264664");
        assertionsPercent("0.000008072", "-96787", "-0.00781264664");
        assertionsPercent("-0.000008072", "96787", "-0.00781264664");
        assertionsPercent("-0.000008072", "-96787", "0.00781264664");

        assertionsPercent("0.000009112", "41", "0.00000373592");
        assertionsPercent("0.000009112", "-41", "-0.00000373592");
        assertionsPercent("-0.000009112", "41", "-0.00000373592");
        assertionsPercent("-0.000009112", "-41", "0.00000373592");

        assertionsPercent("0.000009214", "9733", "0.00089679862");
        assertionsPercent("0.000009214", "-9733", "-0.00089679862");
        assertionsPercent("-0.000009214", "9733", "-0.00089679862");
        assertionsPercent("-0.000009214", "-9733", "0.00089679862");

        assertionsPercent("0.000055555", "55555", "0.03086358025");
        assertionsPercent("0.000055555", "-55555", "-0.03086358025");
        assertionsPercent("-0.000055555", "55555", "-0.03086358025");
        assertionsPercent("-0.000055555", "-55555", "0.03086358025");

        assertionsPercent("0.000082534", "88", "0.00007262992");
        assertionsPercent("0.000082534", "-88", "-0.00007262992");
        assertionsPercent("-0.000082534", "88", "-0.00007262992");
        assertionsPercent("-0.000082534", "-88", "0.00007262992");

        assertionsPercent("0.072324911", "23443", "16.95512888573");
        assertionsPercent("0.072324911", "-23443", "-16.95512888573");
        assertionsPercent("-0.072324911", "23443", "-16.95512888573");
        assertionsPercent("-0.072324911", "-23443", "16.95512888573");

        assertionsPercent("0.888888888", "888888888", "7901234.55209876544");
        assertionsPercent("0.888888888", "-888888888", "-7901234.55209876544");
        assertionsPercent("-0.888888888", "888888888", "-7901234.55209876544");
        assertionsPercent("-0.888888888", "-888888888", "7901234.55209876544");

        assertionsPercent("6.243776665", "637", "39.77285735605");
        assertionsPercent("6.243776665", "-637", "-39.77285735605");
        assertionsPercent("-6.243776665", "637", "-39.77285735605");
        assertionsPercent("-6.243776665", "-637", "39.77285735605");

        assertionsPercent("999.999999999", "999999999999", "9999999999980.00000000001");
        assertionsPercent("999.999999999", "-999999999999", "-9999999999980.00000000001");
        assertionsPercent("-999.999999999", "999999999999", "-9999999999980.00000000001");
        assertionsPercent("-999.999999999", "-999999999999", "9999999999980.00000000001");

        assertionsPercent("8407.407347404", "84074073474070", "7068449830520798.5767581428");
        assertionsPercent("8407.407347404", "-84074073474070", "-7068449830520798.5767581428");
        assertionsPercent("-8407.407347404", "84074073474070", "-7068449830520798.5767581428");
        assertionsPercent("-8407.407347404", "-84074073474070", "7068449830520798.5767581428");

        assertionsPercent("96419.752386415", "964197523864192", "929676865025798605.04365751680");
        assertionsPercent("96419.752386415", "-964197523864192", "-929676865025798605.04365751680");
        assertionsPercent("-96419.752386415", "964197523864192", "-929676865025798605.04365751680");
        assertionsPercent("-96419.752386415", "-964197523864192", "929676865025798605.04365751680");

        assertionsPercent("10289622.287797479", "349675", "35980236734.85583469325");
        assertionsPercent("10289622.287797479", "-349675", "-35980236734.85583469325");
        assertionsPercent("-10289622.287797479", "349675", "-35980236734.85583469325");
        assertionsPercent("-10289622.287797479", "-349675", "35980236734.85583469325");

        assertionsPercent("1E-17", "8787676", "8.787676E-13");
        assertionsPercent("1E-17", "-8787676", "-8.787676E-13");
        assertionsPercent("-1E-17", "8787676", "-8.787676E-13");
        assertionsPercent("-1E-17", "-8787676", "8.787676E-13");

        assertionsPercent("5647753E-17", "6567687746", "0.0037092678170534738");
        assertionsPercent("5647753E-17", "-6567687746", "-0.0037092678170534738");
        assertionsPercent("-5647753E-17", "6567687746", "-0.0037092678170534738");
        assertionsPercent("-5647753E-17", "-6567687746", "0.0037092678170534738");

        assertionsPercent("1E-296", "8787676", "8.787676E-292");
        assertionsPercent("1E-296", "-8787676", "-8.787676E-292");
        assertionsPercent("-1E-296", "8787676", "-8.787676E-292");
        assertionsPercent("-1E-296", "-8787676", "8.787676E-292");

        assertionsPercent("5647753E-543", "6567687746", "3.7092678170534738E-529");
        assertionsPercent("5647753E-543", "-6567687746", "-3.7092678170534738E-529");
        assertionsPercent("-5647753E-543", "6567687746", "-3.7092678170534738E-529");
        assertionsPercent("-5647753E-543", "-6567687746", "3.7092678170534738E-529");

        assertionsPercent("1E-1296", "8787676", "8.787676E-1292");
        assertionsPercent("1E-1296", "-8787676", "-8.787676E-1292");
        assertionsPercent("-1E-1296", "8787676", "-8.787676E-1292");
        assertionsPercent("-1E-1296", "-8787676", "8.787676E-1292");

        assertionsPercent("5647753E-1543", "6567687746", "3.7092678170534738E-1529");
        assertionsPercent("5647753E-1543", "-6567687746", "-3.7092678170534738E-1529");
        assertionsPercent("-5647753E-1543", "6567687746", "-3.7092678170534738E-1529");
        assertionsPercent("-5647753E-1543", "-6567687746", "3.7092678170534738E-1529");

        assertionsPercent("1E-2296", "8787676", "8.787676E-2292");
        assertionsPercent("1E-2296", "-8787676", "-8.787676E-2292");
        assertionsPercent("-1E-2296", "8787676", "-8.787676E-2292");
        assertionsPercent("-1E-2296", "-8787676", "8.787676E-2292");

        assertionsPercent("5647753E-2543", "6567687746", "3.7092678170534738E-2529");
        assertionsPercent("5647753E-2543", "-6567687746", "-3.7092678170534738E-2529");
        assertionsPercent("-5647753E-2543", "6567687746", "-3.7092678170534738E-2529");
        assertionsPercent("-5647753E-2543", "-6567687746", "3.7092678170534738E-2529");

        assertionsPercent("1E-3296", "8787676", "8.787676E-3292");
        assertionsPercent("1E-3296", "-8787676", "-8.787676E-3292");
        assertionsPercent("-1E-3296", "8787676", "-8.787676E-3292");
        assertionsPercent("-1E-3296", "-8787676", "8.787676E-3292");

        assertionsPercent("5647753E-3543", "6567687746", "3.7092678170534738E-3529");
        assertionsPercent("5647753E-3543", "-6567687746", "-3.7092678170534738E-3529");
        assertionsPercent("-5647753E-3543", "6567687746", "-3.7092678170534738E-3529");
        assertionsPercent("-5647753E-3543", "-6567687746", "3.7092678170534738E-3529");

        assertionsPercent("1E-4296", "8787676", "8.787676E-4292");
        assertionsPercent("1E-4296", "-8787676", "-8.787676E-4292");
        assertionsPercent("-1E-4296", "8787676", "-8.787676E-4292");
        assertionsPercent("-1E-4296", "-8787676", "8.787676E-4292");

        assertionsPercent("5647753E-4543", "6567687746", "3.7092678170534738E-4529");
        assertionsPercent("5647753E-4543", "-6567687746", "-3.7092678170534738E-4529");
        assertionsPercent("-5647753E-4543", "6567687746", "-3.7092678170534738E-4529");
        assertionsPercent("-5647753E-4543", "-6567687746", "3.7092678170534738E-4529");

        assertionsPercent("1E-5296", "8787676", "8.787676E-5292");
        assertionsPercent("1E-5296", "-8787676", "-8.787676E-5292");
        assertionsPercent("-1E-5296", "8787676", "-8.787676E-5292");
        assertionsPercent("-1E-5296", "-8787676", "8.787676E-5292");

        assertionsPercent("5647753E-5543", "6567687746", "3.7092678170534738E-5529");
        assertionsPercent("5647753E-5543", "-6567687746", "-3.7092678170534738E-5529");
        assertionsPercent("-5647753E-5543", "6567687746", "-3.7092678170534738E-5529");
        assertionsPercent("-5647753E-5543", "-6567687746", "3.7092678170534738E-5529");

        assertionsPercent("1E-6296", "8787676", "8.787676E-6292");
        assertionsPercent("1E-6296", "-8787676", "-8.787676E-6292");
        assertionsPercent("-1E-6296", "8787676", "-8.787676E-6292");
        assertionsPercent("-1E-6296", "-8787676", "8.787676E-6292");

        assertionsPercent("5647753E-6543", "6567687746", "3.7092678170534738E-6529");
        assertionsPercent("5647753E-6543", "-6567687746", "-3.7092678170534738E-6529");
        assertionsPercent("-5647753E-6543", "6567687746", "-3.7092678170534738E-6529");
        assertionsPercent("-5647753E-6543", "-6567687746", "3.7092678170534738E-6529");

        assertionsPercent("1E-7296", "8787676", "8.787676E-7292");
        assertionsPercent("1E-7296", "-8787676", "-8.787676E-7292");
        assertionsPercent("-1E-7296", "8787676", "-8.787676E-7292");
        assertionsPercent("-1E-7296", "-8787676", "8.787676E-7292");

        assertionsPercent("5647753E-7543", "6567687746", "3.7092678170534738E-7529");
        assertionsPercent("5647753E-7543", "-6567687746", "-3.7092678170534738E-7529");
        assertionsPercent("-5647753E-7543", "6567687746", "-3.7092678170534738E-7529");
        assertionsPercent("-5647753E-7543", "-6567687746", "3.7092678170534738E-7529");

        assertionsPercent("1E-8296", "8787676", "8.787676E-8292");
        assertionsPercent("1E-8296", "-8787676", "-8.787676E-8292");
        assertionsPercent("-1E-8296", "8787676", "-8.787676E-8292");
        assertionsPercent("-1E-8296", "-8787676", "8.787676E-8292");

        assertionsPercent("5647753E-8543", "6567687746", "3.7092678170534738E-8529");
        assertionsPercent("5647753E-8543", "-6567687746", "-3.7092678170534738E-8529");
        assertionsPercent("-5647753E-8543", "6567687746", "-3.7092678170534738E-8529");
        assertionsPercent("-5647753E-8543", "-6567687746", "3.7092678170534738E-8529");

        assertionsPercent("1E-9296", "8787676", "8.787676E-9292");
        assertionsPercent("1E-9296", "-8787676", "-8.787676E-9292");
        assertionsPercent("-1E-9296", "8787676", "-8.787676E-9292");
        assertionsPercent("-1E-9296", "-8787676", "8.787676E-9292");

        assertionsPercent("5647753E-9543", "6567687746", "3.7092678170534738E-9529");
        assertionsPercent("5647753E-9543", "-6567687746", "-3.7092678170534738E-9529");
        assertionsPercent("-5647753E-9543", "6567687746", "-3.7092678170534738E-9529");
        assertionsPercent("-5647753E-9543", "-6567687746", "3.7092678170534738E-9529");

        assertionsPercent("1E-10296", "8787676", "8.787676E-10292");
        assertionsPercent("1E-10296", "-8787676", "-8.787676E-10292");
        assertionsPercent("-1E-10296", "8787676", "-8.787676E-10292");
        assertionsPercent("-1E-10296", "-8787676", "8.787676E-10292");

        assertionsPercent("5647753E-10543", "6567687746", "3.7092678170534738E-10529");
        assertionsPercent("5647753E-10543", "-6567687746", "-3.7092678170534738E-10529");
        assertionsPercent("-5647753E-10543", "6567687746", "-3.7092678170534738E-10529");
        assertionsPercent("-5647753E-10543", "-6567687746", "3.7092678170534738E-10529");

        //second operand is decimal
        assertionsPercent("1", "0.2", "0.002");
        assertionsPercent("1", "-0.2", "-0.002");
        assertionsPercent("-1", "0.2", "-0.002");
        assertionsPercent("-1", "-0.2", "0.002");

        assertionsPercent("2", "0.010", "0.0002");
        assertionsPercent("2", "-0.010", "-0.0002");
        assertionsPercent("-2", "0.010", "-0.0002");
        assertionsPercent("-2", "-0.010", "0.0002");

        assertionsPercent("3", "0.003", "0.00009");
        assertionsPercent("3", "-0.003", "-0.00009");
        assertionsPercent("-3", "0.003", "-0.00009");
        assertionsPercent("-3", "-0.003", "0.00009");

        assertionsPercent("4", "0.004", "0.00016");
        assertionsPercent("4", "-0.004", "-0.00016");
        assertionsPercent("-4", "0.004", "-0.00016");
        assertionsPercent("-4", "-0.004", "0.00016");

        assertionsPercent("5", "0.005", "0.00025");
        assertionsPercent("5", "-0.005", "-0.00025");
        assertionsPercent("-5", "0.005", "-0.00025");
        assertionsPercent("-5", "-0.005", "0.00025");

        assertionsPercent("6", "0.006", "0.00036");
        assertionsPercent("6", "-0.006", "-0.00036");
        assertionsPercent("-6", "0.006", "-0.00036");
        assertionsPercent("-6", "-0.006", "0.00036");

        assertionsPercent("7", "0.007", "0.00049");
        assertionsPercent("7", "-0.007", "-0.00049");
        assertionsPercent("-7", "0.007", "-0.00049");
        assertionsPercent("-7", "-0.007", "0.00049");

        assertionsPercent("8", "0.008", "0.00064");
        assertionsPercent("8", "-0.008", "-0.00064");
        assertionsPercent("-8", "0.008", "-0.00064");
        assertionsPercent("-8", "-0.008", "0.00064");

        assertionsPercent("9", "0.009", "0.00081");
        assertionsPercent("9", "-0.009", "-0.00081");
        assertionsPercent("-9", "0.009", "-0.00081");
        assertionsPercent("-9", "-0.009", "0.00081");

        assertionsPercent("10", "0.100", "0.010");
        assertionsPercent("10", "-0.100", "-0.010");
        assertionsPercent("-10", "0.100", "-0.010");
        assertionsPercent("-10", "-0.100", "0.010");

        assertionsPercent("11", "0.00011", "0.0000121");
        assertionsPercent("11", "-0.00011", "-0.0000121");
        assertionsPercent("-11", "0.00011", "-0.0000121");
        assertionsPercent("-11", "-0.00011", "0.0000121");

        assertionsPercent("43", "0.00433", "0.0018619");
        assertionsPercent("43", "-0.00433", "-0.0018619");
        assertionsPercent("-43", "0.00433", "-0.0018619");
        assertionsPercent("-43", "-0.00433", "0.0018619");

        assertionsPercent("98", "0.00994", "0.0097412");
        assertionsPercent("98", "-0.00994", "-0.0097412");
        assertionsPercent("-98", "0.00994", "-0.0097412");
        assertionsPercent("-98", "-0.00994", "0.0097412");

        assertionsPercent("152", "0.00077", "0.0011704");
        assertionsPercent("152", "-0.00077", "-0.0011704");
        assertionsPercent("-152", "0.00077", "-0.0011704");
        assertionsPercent("-152", "-0.00077", "0.0011704");

        assertionsPercent("278", "0.00008", "0.0002224");
        assertionsPercent("278", "-0.00008", "-0.0002224");
        assertionsPercent("-278", "0.00008", "-0.0002224");
        assertionsPercent("-278", "-0.00008", "0.0002224");

        assertionsPercent("367", "0.00089", "0.0032663");
        assertionsPercent("367", "-0.00089", "-0.0032663");
        assertionsPercent("-367", "0.00089", "-0.0032663");
        assertionsPercent("-367", "-0.00089", "0.0032663");

        assertionsPercent("547", "0.00021", "0.0011487");
        assertionsPercent("547", "-0.00021", "-0.0011487");
        assertionsPercent("-547", "0.00021", "-0.0011487");
        assertionsPercent("-547", "-0.00021", "0.0011487");

        assertionsPercent("562", "0.00052", "0.0029224");
        assertionsPercent("562", "-0.00052", "-0.0029224");
        assertionsPercent("-562", "0.00052", "-0.0029224");
        assertionsPercent("-562", "-0.00052", "0.0029224");

        assertionsPercent("679", "0.00511", "0.0346969");
        assertionsPercent("679", "-0.00511", "-0.0346969");
        assertionsPercent("-679", "0.00511", "-0.0346969");
        assertionsPercent("-679", "-0.00511", "0.0346969");

        assertionsPercent("742", "0.00056", "0.0041552");
        assertionsPercent("742", "-0.00056", "-0.0041552");
        assertionsPercent("-742", "0.00056", "-0.0041552");
        assertionsPercent("-742", "-0.00056", "0.0041552");

        assertionsPercent("893", "0.04223", "0.3771139");
        assertionsPercent("893", "-0.04223", "-0.3771139");
        assertionsPercent("-893", "0.04223", "-0.3771139");
        assertionsPercent("-893", "-0.04223", "0.3771139");

        assertionsPercent("935", "0.00563", "0.0526405");
        assertionsPercent("935", "-0.00563", "-0.0526405");
        assertionsPercent("-935", "0.00563", "-0.0526405");
        assertionsPercent("-935", "-0.00563", "0.0526405");

        assertionsPercent("952", "0.00096", "0.0091392");
        assertionsPercent("952", "-0.00096", "-0.0091392");
        assertionsPercent("-952", "0.00096", "-0.0091392");
        assertionsPercent("-952", "-0.00096", "0.0091392");

        assertionsPercent("999", "0.00999", "0.0998001");
        assertionsPercent("999", "-0.00999", "-0.0998001");
        assertionsPercent("-999", "0.00999", "-0.0998001");
        assertionsPercent("-999", "-0.00999", "0.0998001");

        assertionsPercent("1008", "7.3E-7", "0.0000073584");
        assertionsPercent("1008", "-7.3E-7", "-0.0000073584");
        assertionsPercent("-1008", "7.3E-7", "-0.0000073584");
        assertionsPercent("-1008", "-7.3E-7", "0.0000073584");

        assertionsPercent("2256", "874.96874762", "19739.2949463072");
        assertionsPercent("2256", "-874.96874762", "-19739.2949463072");
        assertionsPercent("-2256", "874.96874762", "-19739.2949463072");
        assertionsPercent("-2256", "-874.96874762", "19739.2949463072");

        assertionsPercent("3173", "5.7E-7", "0.0000180861");
        assertionsPercent("3173", "-5.7E-7", "-0.0000180861");
        assertionsPercent("-3173", "5.7E-7", "-0.0000180861");
        assertionsPercent("-3173", "-5.7E-7", "0.0000180861");

        assertionsPercent("3477", "0.00000875", "0.0003042375");
        assertionsPercent("3477", "-0.00000875", "-0.0003042375");
        assertionsPercent("-3477", "0.00000875", "-0.0003042375");
        assertionsPercent("-3477", "-0.00000875", "0.0003042375");

        assertionsPercent("3889", "3.4E-7", "0.0000132226");
        assertionsPercent("3889", "-3.4E-7", "-0.0000132226");
        assertionsPercent("-3889", "3.4E-7", "-0.0000132226");
        assertionsPercent("-3889", "-3.4E-7", "0.0000132226");

        assertionsPercent("4378", "0.00356653", "0.1561426834");
        assertionsPercent("4378", "-0.00356653", "-0.1561426834");
        assertionsPercent("-4378", "0.00356653", "-0.1561426834");
        assertionsPercent("-4378", "-0.00356653", "0.1561426834");

        assertionsPercent("4647", "745.56387735", "34646.3533804545");
        assertionsPercent("4647", "-745.56387735", "-34646.3533804545");
        assertionsPercent("-4647", "745.56387735", "-34646.3533804545");
        assertionsPercent("-4647", "-745.56387735", "34646.3533804545");

        assertionsPercent("5448", "0.00054448", "0.0296632704");
        assertionsPercent("5448", "-0.00054448", "-0.0296632704");
        assertionsPercent("-5448", "0.00054448", "-0.0296632704");
        assertionsPercent("-5448", "-0.00054448", "0.0296632704");

        assertionsPercent("6271", "3.1E-7", "0.0000194401");
        assertionsPercent("6271", "-3.1E-7", "-0.0000194401");
        assertionsPercent("-6271", "3.1E-7", "-0.0000194401");
        assertionsPercent("-6271", "-3.1E-7", "0.0000194401");

        assertionsPercent("7118", "0.09186032", "6.5386175776");
        assertionsPercent("7118", "-0.09186032", "-6.5386175776");
        assertionsPercent("-7118", "0.09186032", "-6.5386175776");
        assertionsPercent("-7118", "-0.09186032", "6.5386175776");

        assertionsPercent("8072", "0.00096787", "0.0781264664");
        assertionsPercent("8072", "-0.00096787", "-0.0781264664");
        assertionsPercent("-8072", "0.00096787", "-0.0781264664");
        assertionsPercent("-8072", "-0.00096787", "0.0781264664");

        assertionsPercent("9112", "4.1E-7", "0.0000373592");
        assertionsPercent("9112", "-4.1E-7", "-0.0000373592");
        assertionsPercent("-9112", "4.1E-7", "-0.0000373592");
        assertionsPercent("-9112", "-4.1E-7", "0.0000373592");

        assertionsPercent("9214", "0.00009733", "0.0089679862");
        assertionsPercent("9214", "-0.00009733", "-0.0089679862");
        assertionsPercent("-9214", "0.00009733", "-0.0089679862");
        assertionsPercent("-9214", "-0.00009733", "0.0089679862");

        assertionsPercent("55555", "0.00055555", "0.3086358025");
        assertionsPercent("55555", "-0.00055555", "-0.3086358025");
        assertionsPercent("-55555", "0.00055555", "-0.3086358025");
        assertionsPercent("-55555", "-0.00055555", "0.3086358025");

        assertionsPercent("82534", "8.8E-7", "0.0007262992");
        assertionsPercent("82534", "-8.8E-7", "-0.0007262992");
        assertionsPercent("-82534", "8.8E-7", "-0.0007262992");
        assertionsPercent("-82534", "-8.8E-7", "0.0007262992");

        assertionsPercent("72324911", "0.00023443", "169.5512888573");
        assertionsPercent("72324911", "-0.00023443", "-169.5512888573");
        assertionsPercent("-72324911", "0.00023443", "-169.5512888573");
        assertionsPercent("-72324911", "-0.00023443", "169.5512888573");

        assertionsPercent("888888888", "8.88888888", "79012345.5209876544");
        assertionsPercent("888888888", "-8.88888888", "-79012345.5209876544");
        assertionsPercent("-888888888", "8.88888888", "-79012345.5209876544");
        assertionsPercent("-888888888", "-8.88888888", "79012345.5209876544");

        assertionsPercent("6243776665", "0.00000637", "397.7285735605");
        assertionsPercent("6243776665", "-0.00000637", "-397.7285735605");
        assertionsPercent("-6243776665", "0.00000637", "-397.7285735605");
        assertionsPercent("-6243776665", "-0.00000637", "397.7285735605");

        assertionsPercent("999999999999", "9999.99999999", "99999999999800.0000000001");
        assertionsPercent("999999999999", "-9999.99999999", "-99999999999800.0000000001");
        assertionsPercent("-999999999999", "9999.99999999", "-99999999999800.0000000001");
        assertionsPercent("-999999999999", "-9999.99999999", "99999999999800.0000000001");

        assertionsPercent("8407407347404", "840740.73474070", "70684498305207985.767581428");
        assertionsPercent("8407407347404", "-840740.73474070", "-70684498305207985.767581428");
        assertionsPercent("-8407407347404", "840740.73474070", "-70684498305207985.767581428");
        assertionsPercent("-8407407347404", "-840740.73474070", "70684498305207985.767581428");

        assertionsPercent("96419752386415", "9641975.23864192", "9296768650257986050.4365751680");
        assertionsPercent("96419752386415", "-9641975.23864192", "-9296768650257986050.4365751680");
        assertionsPercent("-96419752386415", "9641975.23864192", "-9296768650257986050.4365751680");
        assertionsPercent("-96419752386415", "-9641975.23864192", "9296768650257986050.4365751680");

        assertionsPercent("10289622287797479", "0.00349675", "359802367348.5583469325");
        assertionsPercent("10289622287797479", "-0.00349675", "-359802367348.5583469325");
        assertionsPercent("-10289622287797479", "0.00349675", "-359802367348.5583469325");
        assertionsPercent("-10289622287797479", "-0.00349675", "359802367348.5583469325");

        assertionsPercent("0.1", "0.2", "0.0002");
        assertionsPercent("0.1", "-0.2", "-0.0002");
        assertionsPercent("-0.1", "0.2", "-0.0002");
        assertionsPercent("-0.1", "-0.2", "0.0002");

        //both operands are decimal
        assertionsPercent("0.002", "0.010", "2E-7");
        assertionsPercent("0.002", "-0.010", "-2E-7");
        assertionsPercent("-0.002", "0.010", "-2E-7");
        assertionsPercent("-0.002", "-0.010", "2E-7");

        assertionsPercent("0.003", "0.003", "9E-8");
        assertionsPercent("0.003", "-0.003", "-9E-8");
        assertionsPercent("-0.003", "0.003", "-9E-8");
        assertionsPercent("-0.003", "-0.003", "9E-8");

        assertionsPercent("0.004", "0.004", "1.6E-7");
        assertionsPercent("0.004", "-0.004", "-1.6E-7");
        assertionsPercent("-0.004", "0.004", "-1.6E-7");
        assertionsPercent("-0.004", "-0.004", "1.6E-7");

        assertionsPercent("0.005", "0.005", "2.5E-7");
        assertionsPercent("0.005", "-0.005", "-2.5E-7");
        assertionsPercent("-0.005", "0.005", "-2.5E-7");
        assertionsPercent("-0.005", "-0.005", "2.5E-7");

        assertionsPercent("0.006", "0.006", "3.6E-7");
        assertionsPercent("0.006", "-0.006", "-3.6E-7");
        assertionsPercent("-0.006", "0.006", "-3.6E-7");
        assertionsPercent("-0.006", "-0.006", "3.6E-7");

        assertionsPercent("0.007", "0.007", "4.9E-7");
        assertionsPercent("0.007", "-0.007", "-4.9E-7");
        assertionsPercent("-0.007", "0.007", "-4.9E-7");
        assertionsPercent("-0.007", "-0.007", "4.9E-7");

        assertionsPercent("0.008", "0.008", "6.4E-7");
        assertionsPercent("0.008", "-0.008", "-6.4E-7");
        assertionsPercent("-0.008", "0.008", "-6.4E-7");
        assertionsPercent("-0.008", "-0.008", "6.4E-7");

        assertionsPercent("0.009", "0.009", "8.1E-7");
        assertionsPercent("0.009", "-0.009", "-8.1E-7");
        assertionsPercent("-0.009", "0.009", "-8.1E-7");
        assertionsPercent("-0.009", "-0.009", "8.1E-7");

        assertionsPercent("0.010", "0.100", "0.000010");
        assertionsPercent("0.010", "-0.100", "-0.000010");
        assertionsPercent("-0.010", "0.100", "-0.000010");
        assertionsPercent("-0.010", "-0.100", "0.000010");

        assertionsPercent("0.00011", "0.00011", "1.21E-10");
        assertionsPercent("0.00011", "-0.00011", "-1.21E-10");
        assertionsPercent("-0.00011", "0.00011", "-1.21E-10");
        assertionsPercent("-0.00011", "-0.00011", "1.21E-10");

        assertionsPercent("0.00043", "0.00433", "1.8619E-8");
        assertionsPercent("0.00043", "-0.00433", "-1.8619E-8");
        assertionsPercent("-0.00043", "0.00433", "-1.8619E-8");
        assertionsPercent("-0.00043", "-0.00433", "1.8619E-8");

        assertionsPercent("0.00098", "0.00994", "9.7412E-8");
        assertionsPercent("0.00098", "-0.00994", "-9.7412E-8");
        assertionsPercent("-0.00098", "0.00994", "-9.7412E-8");
        assertionsPercent("-0.00098", "-0.00994", "9.7412E-8");

        assertionsPercent("0.00152", "0.00077", "1.1704E-8");
        assertionsPercent("0.00152", "-0.00077", "-1.1704E-8");
        assertionsPercent("-0.00152", "0.00077", "-1.1704E-8");
        assertionsPercent("-0.00152", "-0.00077", "1.1704E-8");

        assertionsPercent("0.00278", "0.00008", "2.224E-9");
        assertionsPercent("0.00278", "-0.00008", "-2.224E-9");
        assertionsPercent("-0.00278", "0.00008", "-2.224E-9");
        assertionsPercent("-0.00278", "-0.00008", "2.224E-9");

        assertionsPercent("0.00367", "0.00089", "3.2663E-8");
        assertionsPercent("0.00367", "-0.00089", "-3.2663E-8");
        assertionsPercent("-0.00367", "0.00089", "-3.2663E-8");
        assertionsPercent("-0.00367", "-0.00089", "3.2663E-8");

        assertionsPercent("0.00547", "0.00021", "1.1487E-8");
        assertionsPercent("0.00547", "-0.00021", "-1.1487E-8");
        assertionsPercent("-0.00547", "0.00021", "-1.1487E-8");
        assertionsPercent("-0.00547", "-0.00021", "1.1487E-8");

        assertionsPercent("0.00562", "0.00052", "2.9224E-8");
        assertionsPercent("0.00562", "-0.00052", "-2.9224E-8");
        assertionsPercent("-0.00562", "0.00052", "-2.9224E-8");
        assertionsPercent("-0.00562", "-0.00052", "2.9224E-8");

        assertionsPercent("0.00679", "0.00511", "3.46969E-7");
        assertionsPercent("0.00679", "-0.00511", "-3.46969E-7");
        assertionsPercent("-0.00679", "0.00511", "-3.46969E-7");
        assertionsPercent("-0.00679", "-0.00511", "3.46969E-7");

        assertionsPercent("0.00742", "0.00056", "4.1552E-8");
        assertionsPercent("0.00742", "-0.00056", "-4.1552E-8");
        assertionsPercent("-0.00742", "0.00056", "-4.1552E-8");
        assertionsPercent("-0.00742", "-0.00056", "4.1552E-8");

        assertionsPercent("0.00893", "0.04223", "0.000003771139");
        assertionsPercent("0.00893", "-0.04223", "-0.000003771139");
        assertionsPercent("-0.00893", "0.04223", "-0.000003771139");
        assertionsPercent("-0.00893", "-0.04223", "0.000003771139");

        assertionsPercent("0.00935", "0.00563", "5.26405E-7");
        assertionsPercent("0.00935", "-0.00563", "-5.26405E-7");
        assertionsPercent("-0.00935", "0.00563", "-5.26405E-7");
        assertionsPercent("-0.00935", "-0.00563", "5.26405E-7");

        assertionsPercent("0.00952", "0.00096", "9.1392E-8");
        assertionsPercent("0.00952", "-0.00096", "-9.1392E-8");
        assertionsPercent("-0.00952", "0.00096", "-9.1392E-8");
        assertionsPercent("-0.00952", "-0.00096", "9.1392E-8");

        assertionsPercent("0.00999", "0.00999", "9.98001E-7");
        assertionsPercent("0.00999", "-0.00999", "-9.98001E-7");
        assertionsPercent("-0.00999", "0.00999", "-9.98001E-7");
        assertionsPercent("-0.00999", "-0.00999", "9.98001E-7");

        assertionsPercent("10.08", "7.3E-8", "7.3584E-9");
        assertionsPercent("10.08", "-7.3E-8", "-7.3584E-9");
        assertionsPercent("-10.08", "7.3E-8", "-7.3584E-9");
        assertionsPercent("-10.08", "-7.3E-8", "7.3584E-9");

        assertionsPercent("22.56", "87.496874762", "19.7392949463072");
        assertionsPercent("22.56", "-87.496874762", "-19.7392949463072");
        assertionsPercent("-22.56", "87.496874762", "-19.7392949463072");
        assertionsPercent("-22.56", "-87.496874762", "19.7392949463072");

        assertionsPercent("31.73", "5.7E-8", "1.80861E-8");
        assertionsPercent("31.73", "-5.7E-8", "-1.80861E-8");
        assertionsPercent("-31.73", "5.7E-8", "-1.80861E-8");
        assertionsPercent("-31.73", "-5.7E-8", "1.80861E-8");

        assertionsPercent("34.77", "8.75E-7", "3.042375E-7");
        assertionsPercent("34.77", "-8.75E-7", "-3.042375E-7");
        assertionsPercent("-34.77", "8.75E-7", "-3.042375E-7");
        assertionsPercent("-34.77", "-8.75E-7", "3.042375E-7");

        assertionsPercent("38.89", "3.4E-8", "1.32226E-8");
        assertionsPercent("38.89", "-3.4E-8", "-1.32226E-8");
        assertionsPercent("-38.89", "3.4E-8", "-1.32226E-8");
        assertionsPercent("-38.89", "-3.4E-8", "1.32226E-8");

        assertionsPercent("43.78", "0.000356653", "0.0001561426834");
        assertionsPercent("43.78", "-0.000356653", "-0.0001561426834");
        assertionsPercent("-43.78", "0.000356653", "-0.0001561426834");
        assertionsPercent("-43.78", "-0.000356653", "0.0001561426834");

        assertionsPercent("46.47", "74.556387735", "34.6463533804545");
        assertionsPercent("46.47", "-74.556387735", "-34.6463533804545");
        assertionsPercent("-46.47", "74.556387735", "-34.6463533804545");
        assertionsPercent("-46.47", "-74.556387735", "34.6463533804545");

        assertionsPercent("54.48", "0.000054448", "0.0000296632704");
        assertionsPercent("54.48", "-0.000054448", "-0.0000296632704");
        assertionsPercent("-54.48", "0.000054448", "-0.0000296632704");
        assertionsPercent("-54.48", "-0.000054448", "0.0000296632704");

        assertionsPercent("62.71", "3.1E-8", "1.94401E-8");
        assertionsPercent("62.71", "-3.1E-8", "-1.94401E-8");
        assertionsPercent("-62.71", "3.1E-8", "-1.94401E-8");
        assertionsPercent("-62.71", "-3.1E-8", "1.94401E-8");

        assertionsPercent("71.18", "0.009186032", "0.0065386175776");
        assertionsPercent("71.18", "-0.009186032", "-0.0065386175776");
        assertionsPercent("-71.18", "0.009186032", "-0.0065386175776");
        assertionsPercent("-71.18", "-0.009186032", "0.0065386175776");

        assertionsPercent("80.72", "0.000096787", "0.0000781264664");
        assertionsPercent("80.72", "-0.000096787", "-0.0000781264664");
        assertionsPercent("-80.72", "0.000096787", "-0.0000781264664");
        assertionsPercent("-80.72", "-0.000096787", "0.0000781264664");

        assertionsPercent("91.12", "4.1E-8", "3.73592E-8");
        assertionsPercent("91.12", "-4.1E-8", "-3.73592E-8");
        assertionsPercent("-91.12", "4.1E-8", "-3.73592E-8");
        assertionsPercent("-91.12", "-4.1E-8", "3.73592E-8");

        assertionsPercent("92.14", "0.000009733", "0.0000089679862");
        assertionsPercent("92.14", "-0.000009733", "-0.0000089679862");
        assertionsPercent("-92.14", "0.000009733", "-0.0000089679862");
        assertionsPercent("-92.14", "-0.000009733", "0.0000089679862");

        assertionsPercent("555.55", "0.000055555", "0.0003086358025");
        assertionsPercent("555.55", "-0.000055555", "-0.0003086358025");
        assertionsPercent("-555.55", "0.000055555", "-0.0003086358025");
        assertionsPercent("-555.55", "-0.000055555", "0.0003086358025");

        assertionsPercent("825.34", "8.8E-8", "7.262992E-7");
        assertionsPercent("825.34", "-8.8E-8", "-7.262992E-7");
        assertionsPercent("-825.34", "8.8E-8", "-7.262992E-7");
        assertionsPercent("-825.34", "-8.8E-8", "7.262992E-7");

        assertionsPercent("723249.11", "0.000023443", "0.1695512888573");
        assertionsPercent("723249.11", "-0.000023443", "-0.1695512888573");
        assertionsPercent("-723249.11", "0.000023443", "-0.1695512888573");
        assertionsPercent("-723249.11", "-0.000023443", "0.1695512888573");

        assertionsPercent("8888888.88", "0.888888888", "79012.3455209876544");
        assertionsPercent("8888888.88", "-0.888888888", "-79012.3455209876544");
        assertionsPercent("-8888888.88", "0.888888888", "-79012.3455209876544");
        assertionsPercent("-8888888.88", "-0.888888888", "79012.3455209876544");

        assertionsPercent("62437766.65", "6.37E-7", "0.3977285735605");
        assertionsPercent("62437766.65", "-6.37E-7", "-0.3977285735605");
        assertionsPercent("-62437766.65", "6.37E-7", "-0.3977285735605");
        assertionsPercent("-62437766.65", "-6.37E-7", "0.3977285735605");

        assertionsPercent("9999999999.99", "999.999999999", "99999999999.8000000000001");
        assertionsPercent("9999999999.99", "-999.999999999", "-99999999999.8000000000001");
        assertionsPercent("-9999999999.99", "999.999999999", "-99999999999.8000000000001");
        assertionsPercent("-9999999999.99", "-999.999999999", "99999999999.8000000000001");

        assertionsPercent("84074073474.04", "84074.073474070", "70684498305207.985767581428");
        assertionsPercent("84074073474.04", "-84074.073474070", "-70684498305207.985767581428");
        assertionsPercent("-84074073474.04", "84074.073474070", "-70684498305207.985767581428");
        assertionsPercent("-84074073474.04", "-84074.073474070", "70684498305207.985767581428");

        assertionsPercent("964197523864.15", "964197.523864192", "9296768650257986.0504365751680");
        assertionsPercent("964197523864.15", "-964197.523864192", "-9296768650257986.0504365751680");
        assertionsPercent("-964197523864.15", "964197.523864192", "-9296768650257986.0504365751680");
        assertionsPercent("-964197523864.15", "-964197.523864192", "9296768650257986.0504365751680");

        assertionsPercent("102896222877974.79", "0.000349675", "359802367.3485583469325");
        assertionsPercent("102896222877974.79", "-0.000349675", "-359802367.3485583469325");
        assertionsPercent("-102896222877974.79", "0.000349675", "-359802367.3485583469325");
        assertionsPercent("-102896222877974.79", "-0.000349675", "359802367.3485583469325");

        assertionsPercent("88788779E-17", "755634E-85", "6.7091820230886E-91");
        assertionsPercent("88788779E-17", "-755634E-85", "-6.7091820230886E-91");
        assertionsPercent("-88788779E-17", "755634E-85", "-6.7091820230886E-91");
        assertionsPercent("-88788779E-17", "-755634E-85", "6.7091820230886E-91");

        assertionsPercent("986859867454E-27", "536556E-643", "5.29505583041648424E-655");
        assertionsPercent("986859867454E-27", "-536556E-643", "-5.29505583041648424E-655");
        assertionsPercent("-986859867454E-27", "536556E-643", "-5.29505583041648424E-655");
        assertionsPercent("-986859867454E-27", "-536556E-643", "5.29505583041648424E-655");

        assertionsPercent("88788779E-890", "755634E-85", "6.7091820230886E-964");
        assertionsPercent("88788779E-890", "-755634E-85", "-6.7091820230886E-964");
        assertionsPercent("-88788779E-890", "755634E-85", "-6.7091820230886E-964");
        assertionsPercent("-88788779E-890", "-755634E-85", "6.7091820230886E-964");

        assertionsPercent("986859867454E-967", "536556E-643", "5.29505583041648424E-1595");
        assertionsPercent("986859867454E-967", "-536556E-643", "-5.29505583041648424E-1595");
        assertionsPercent("-986859867454E-967", "536556E-643", "-5.29505583041648424E-1595");
        assertionsPercent("-986859867454E-967", "-536556E-643", "5.29505583041648424E-1595");

        assertionsPercent("88788779E-1890", "755634E-85", "6.7091820230886E-1964");
        assertionsPercent("88788779E-1890", "-755634E-85", "-6.7091820230886E-1964");
        assertionsPercent("-88788779E-1890", "755634E-85", "-6.7091820230886E-1964");
        assertionsPercent("-88788779E-1890", "-755634E-85", "6.7091820230886E-1964");

        assertionsPercent("986859867454E-1967", "536556E-643", "5.29505583041648424E-2595");
        assertionsPercent("986859867454E-1967", "-536556E-643", "-5.29505583041648424E-2595");
        assertionsPercent("-986859867454E-1967", "536556E-643", "-5.29505583041648424E-2595");
        assertionsPercent("-986859867454E-1967", "-536556E-643", "5.29505583041648424E-2595");

        assertionsPercent("88788779E-2890", "755634E-85", "6.7091820230886E-2964");
        assertionsPercent("88788779E-2890", "-755634E-85", "-6.7091820230886E-2964");
        assertionsPercent("-88788779E-2890", "755634E-85", "-6.7091820230886E-2964");
        assertionsPercent("-88788779E-2890", "-755634E-85", "6.7091820230886E-2964");

        assertionsPercent("986859867454E-2967", "536556E-643", "5.29505583041648424E-3595");
        assertionsPercent("986859867454E-2967", "-536556E-643", "-5.29505583041648424E-3595");
        assertionsPercent("-986859867454E-2967", "536556E-643", "-5.29505583041648424E-3595");
        assertionsPercent("-986859867454E-2967", "-536556E-643", "5.29505583041648424E-3595");

        assertionsPercent("88788779E-3890", "755634E-85", "6.7091820230886E-3964");
        assertionsPercent("88788779E-3890", "-755634E-85", "-6.7091820230886E-3964");
        assertionsPercent("-88788779E-3890", "755634E-85", "-6.7091820230886E-3964");
        assertionsPercent("-88788779E-3890", "-755634E-85", "6.7091820230886E-3964");

        assertionsPercent("986859867454E-3967", "536556E-643", "5.29505583041648424E-4595");
        assertionsPercent("986859867454E-3967", "-536556E-643", "-5.29505583041648424E-4595");
        assertionsPercent("-986859867454E-3967", "536556E-643", "-5.29505583041648424E-4595");
        assertionsPercent("-986859867454E-3967", "-536556E-643", "5.29505583041648424E-4595");

        assertionsPercent("88788779E-4890", "755634E-85", "6.7091820230886E-4964");
        assertionsPercent("88788779E-4890", "-755634E-85", "-6.7091820230886E-4964");
        assertionsPercent("-88788779E-4890", "755634E-85", "-6.7091820230886E-4964");
        assertionsPercent("-88788779E-4890", "-755634E-85", "6.7091820230886E-4964");

        assertionsPercent("986859867454E-4967", "536556E-643", "5.29505583041648424E-5595");
        assertionsPercent("986859867454E-4967", "-536556E-643", "-5.29505583041648424E-5595");
        assertionsPercent("-986859867454E-4967", "536556E-643", "-5.29505583041648424E-5595");
        assertionsPercent("-986859867454E-4967", "-536556E-643", "5.29505583041648424E-5595");

        assertionsPercent("88788779E-5890", "755634E-85", "6.7091820230886E-5964");
        assertionsPercent("88788779E-5890", "-755634E-85", "-6.7091820230886E-5964");
        assertionsPercent("-88788779E-5890", "755634E-85", "-6.7091820230886E-5964");
        assertionsPercent("-88788779E-5890", "-755634E-85", "6.7091820230886E-5964");

        assertionsPercent("986859867454E-5967", "536556E-643", "5.29505583041648424E-6595");
        assertionsPercent("986859867454E-5967", "-536556E-643", "-5.29505583041648424E-6595");
        assertionsPercent("-986859867454E-5967", "536556E-643", "-5.29505583041648424E-6595");
        assertionsPercent("-986859867454E-5967", "-536556E-643", "5.29505583041648424E-6595");

        assertionsPercent("88788779E-6890", "755634E-85", "6.7091820230886E-6964");
        assertionsPercent("88788779E-6890", "-755634E-85", "-6.7091820230886E-6964");
        assertionsPercent("-88788779E-6890", "755634E-85", "-6.7091820230886E-6964");
        assertionsPercent("-88788779E-6890", "-755634E-85", "6.7091820230886E-6964");

        assertionsPercent("986859867454E-6967", "536556E-643", "5.29505583041648424E-7595");
        assertionsPercent("986859867454E-6967", "-536556E-643", "-5.29505583041648424E-7595");
        assertionsPercent("-986859867454E-6967", "536556E-643", "-5.29505583041648424E-7595");
        assertionsPercent("-986859867454E-6967", "-536556E-643", "5.29505583041648424E-7595");

        assertionsPercent("88788779E-7890", "755634E-85", "6.7091820230886E-7964");
        assertionsPercent("88788779E-7890", "-755634E-85", "-6.7091820230886E-7964");
        assertionsPercent("-88788779E-7890", "755634E-85", "-6.7091820230886E-7964");
        assertionsPercent("-88788779E-7890", "-755634E-85", "6.7091820230886E-7964");

        assertionsPercent("986859867454E-7967", "536556E-643", "5.29505583041648424E-8595");
        assertionsPercent("986859867454E-7967", "-536556E-643", "-5.29505583041648424E-8595");
        assertionsPercent("-986859867454E-7967", "536556E-643", "-5.29505583041648424E-8595");
        assertionsPercent("-986859867454E-7967", "-536556E-643", "5.29505583041648424E-8595");

        assertionsPercent("88788779E-8890", "755634E-85", "6.7091820230886E-8964");
        assertionsPercent("88788779E-8890", "-755634E-85", "-6.7091820230886E-8964");
        assertionsPercent("-88788779E-8890", "755634E-85", "-6.7091820230886E-8964");
        assertionsPercent("-88788779E-8890", "-755634E-85", "6.7091820230886E-8964");

        assertionsPercent("986859867454E-8967", "536556E-643", "5.29505583041648424E-9595");
        assertionsPercent("986859867454E-8967", "-536556E-643", "-5.29505583041648424E-9595");
        assertionsPercent("-986859867454E-8967", "536556E-643", "-5.29505583041648424E-9595");
        assertionsPercent("-986859867454E-8967", "-536556E-643", "5.29505583041648424E-9595");

        assertionsPercent("88788779E-9890", "755634E-85", "6.7091820230886E-9964");
        assertionsPercent("88788779E-9890", "-755634E-85", "-6.7091820230886E-9964");
        assertionsPercent("-88788779E-9890", "755634E-85", "-6.7091820230886E-9964");
        assertionsPercent("-88788779E-9890", "-755634E-85", "6.7091820230886E-9964");

        assertionsPercent("986859867454E-9967", "536556E-643", "5.29505583041648424E-10595");
        assertionsPercent("986859867454E-9967", "-536556E-643", "-5.29505583041648424E-10595");
        assertionsPercent("-986859867454E-9967", "536556E-643", "-5.29505583041648424E-10595");
        assertionsPercent("-986859867454E-9967", "-536556E-643", "5.29505583041648424E-10595");

        assertionsPercent("88788779E-10890", "755634E-85", "6.7091820230886E-10964");
        assertionsPercent("88788779E-10890", "-755634E-85", "-6.7091820230886E-10964");
        assertionsPercent("-88788779E-10890", "755634E-85", "-6.7091820230886E-10964");
        assertionsPercent("-88788779E-10890", "-755634E-85", "6.7091820230886E-10964");

        assertionsPercent("986859867454E-10967", "536556E-643", "5.29505583041648424E-11595");
        assertionsPercent("986859867454E-10967", "-536556E-643", "-5.29505583041648424E-11595");
        assertionsPercent("-986859867454E-10967", "536556E-643", "-5.29505583041648424E-11595");
        assertionsPercent("-986859867454E-10967", "-536556E-643", "5.29505583041648424E-11595");

    //Special numbers for realization
        //=> 9999999999999999
        assertionsPercent("9999999999999999", "0", "0");
        assertionsPercent("-9999999999999999", "0", "0");
        assertionsPercent("0", "9999999999999999", "0.00");
        assertionsPercent("0", "-9999999999999999", "0.00");

        assertionsPercent("9999999999999999", "1", "99999999999999.99");
        assertionsPercent("9999999999999999", "-1", "-99999999999999.99");
        assertionsPercent("-9999999999999999", "1", "-99999999999999.99");
        assertionsPercent("-9999999999999999", "-1", "99999999999999.99");

        assertionsPercent("9999999999999999", "9999999999999999", "999999999999999800000000000000.01");
        assertionsPercent("9999999999999999", "-9999999999999999", "-999999999999999800000000000000.01");
        assertionsPercent("-9999999999999999", "9999999999999999", "-999999999999999800000000000000.01");
        assertionsPercent("-9999999999999999", "-9999999999999999", "999999999999999800000000000000.01");

        assertionsPercent("9999999999999999", "0.0000000000000001", "0.009999999999999999");
        assertionsPercent("9999999999999999", "-0.0000000000000001", "-0.009999999999999999");
        assertionsPercent("-9999999999999999", "0.0000000000000001", "-0.009999999999999999");
        assertionsPercent("-9999999999999999", "-0.0000000000000001", "0.009999999999999999");

        assertionsPercent("9999999999999999", "9999999999999999E9999", "9.9999999999999980000000000000001E+10028");
        assertionsPercent("9999999999999999", "-9999999999999999E9999", "-9.9999999999999980000000000000001E+10028");
        assertionsPercent("-9999999999999999", "9999999999999999E9999", "-9.9999999999999980000000000000001E+10028");
        assertionsPercent("-9999999999999999", "-9999999999999999E9999", "9.9999999999999980000000000000001E+10028");

        assertionsPercent("9999999999999999", "1E-9999", "9.999999999999999E-9986");
        assertionsPercent("9999999999999999", "-1E-9999", "-9.999999999999999E-9986");
        assertionsPercent("-9999999999999999", "1E-9999", "-9.999999999999999E-9986");
        assertionsPercent("-9999999999999999", "-1E-9999", "9.999999999999999E-9986");

        //=>0.0000000000000001
        assertionsPercent("0.0000000000000001", "1", "1E-18");
        assertionsPercent("0.0000000000000001", "-1", "-1E-18");
        assertionsPercent("-0.0000000000000001", "1", "-1E-18");
        assertionsPercent("-0.0000000000000001", "-1", "1E-18");

        assertionsPercent("0.0000000000000001", "0.0000000000000001", "1E-34");
        assertionsPercent("0.0000000000000001", "-0.0000000000000001", "-1E-34");
        assertionsPercent("-0.0000000000000001", "0.0000000000000001", "-1E-34");
        assertionsPercent("-0.0000000000000001", "-0.0000000000000001", "1E-34");

        assertionsPercent("0.0000000000000001", "9999999999999999", "0.009999999999999999");
        assertionsPercent("0.0000000000000001", "-9999999999999999", "-0.009999999999999999");
        assertionsPercent("-0.0000000000000001", "9999999999999999", "-0.009999999999999999");
        assertionsPercent("-0.0000000000000001", "-9999999999999999", "0.009999999999999999");

        assertionsPercent("0.0000000000000001", "9999999999999999E9999", "9.999999999999999E+9996");
        assertionsPercent("0.0000000000000001", "-9999999999999999E9999", "-9.999999999999999E+9996");
        assertionsPercent("-0.0000000000000001", "9999999999999999E9999", "-9.999999999999999E+9996");
        assertionsPercent("-0.0000000000000001", "-9999999999999999E9999", "9.999999999999999E+9996");

        assertionsPercent("0.0000000000000001", "1E-9999", "1E-10017");
        assertionsPercent("0.0000000000000001", "-1E-9999", "-1E-10017");
        assertionsPercent("-0.0000000000000001", "1E-9999", "-1E-10017");
        assertionsPercent("-0.0000000000000001", "-1E-9999", "1E-10017");

        //=> 9999999999999999E9999
        assertionsPercent("9999999999999999E9999", "1", "9.999999999999999E+10012");
        assertionsPercent("9999999999999999E9999", "-1", "-9.999999999999999E+10012");
        assertionsPercent("-9999999999999999E9999", "1", "-9.999999999999999E+10012");
        assertionsPercent("-9999999999999999E9999", "-1", "9.999999999999999E+10012");

        assertionsPercent("9999999999999999E9999", "9999999999999999E9999", "9.9999999999999980000000000000001E+20027");
        assertionsPercent("9999999999999999E9999", "-9999999999999999E9999", "-9.9999999999999980000000000000001E+20027");
        assertionsPercent("-9999999999999999E9999", "9999999999999999E9999", "-9.9999999999999980000000000000001E+20027");
        assertionsPercent("-9999999999999999E9999", "-9999999999999999E9999", "9.9999999999999980000000000000001E+20027");

        assertionsPercent("9999999999999999E9999", "9999999999999999", "9.9999999999999980000000000000001E+10028");
        assertionsPercent("9999999999999999E9999", "-9999999999999999", "-9.9999999999999980000000000000001E+10028");
        assertionsPercent("-9999999999999999E9999", "9999999999999999", "-9.9999999999999980000000000000001E+10028");
        assertionsPercent("-9999999999999999E9999", "-9999999999999999", "9.9999999999999980000000000000001E+10028");

        assertionsPercent("9999999999999999E9999", "0.0000000000000001", "9.999999999999999E+9996");
        assertionsPercent("9999999999999999E9999", "-0.0000000000000001", "-9.999999999999999E+9996");
        assertionsPercent("-9999999999999999E9999", "0.0000000000000001", "-9.999999999999999E+9996");
        assertionsPercent("-9999999999999999E9999", "-0.0000000000000001", "9.999999999999999E+9996");

        assertionsPercent("9999999999999999E9999", "1E-9999", "99999999999999.99");
        assertionsPercent("9999999999999999E9999", "-1E-9999", "-99999999999999.99");
        assertionsPercent("-9999999999999999E9999", "1E-9999", "-99999999999999.99");
        assertionsPercent("-9999999999999999E9999", "-1E-9999", "99999999999999.99");

        //=> 1E-9999
        assertionsPercent("1E-9999", "1", "1E-10001");
        assertionsPercent("1E-9999", "-1", "-1E-10001");
        assertionsPercent("-1E-9999", "1", "-1E-10001");
        assertionsPercent("-1E-9999", "-1", "1E-10001");

        assertionsPercent("1E-9999", "1E-9999", "1E-20000");
        assertionsPercent("1E-9999", "-1E-9999", "-1E-20000");
        assertionsPercent("-1E-9999", "1E-9999", "-1E-20000");
        assertionsPercent("-1E-9999", "-1E-9999", "1E-20000");

        assertionsPercent("1E-9999", "9999999999999999", "9.999999999999999E-9986");
        assertionsPercent("1E-9999", "-9999999999999999", "-9.999999999999999E-9986");
        assertionsPercent("-1E-9999", "9999999999999999", "-9.999999999999999E-9986");
        assertionsPercent("-1E-9999", "-9999999999999999", "9.999999999999999E-9986");

        assertionsPercent("1E-9999", "0.0000000000000001", "1E-10017");
        assertionsPercent("1E-9999", "-0.0000000000000001", "-1E-10017");
        assertionsPercent("-1E-9999", "0.0000000000000001", "-1E-10017");
        assertionsPercent("-1E-9999", "-0.0000000000000001", "1E-10017");

        assertionsPercent("1E-9999", "9999999999999999E9999", "99999999999999.99");
        assertionsPercent("1E-9999", "-9999999999999999E9999", "-99999999999999.99");
        assertionsPercent("-1E-9999", "9999999999999999E9999", "-99999999999999.99");
        assertionsPercent("-1E-9999", "-9999999999999999E9999", "99999999999999.99");

    }

    void assertionsPercent (String xString, String percentString, String resultString) {
        BigDecimal x = new BigDecimal(xString);
        BigDecimal percent = new BigDecimal(percentString);
        BigDecimal resultExpected = new BigDecimal(resultString);

//        BigDecimal resultActual = binary.getResult();
//        assertEquals(resultExpected, resultActual);

        formula = new ArrayList<>(Arrays.asList(x, OperationsEnum.ADD, percent, OperationsEnum.PERCENT));

        try {
            BigDecimal resultActual = Calculator.calculator();
            assertEquals(resultExpected, resultActual);
        } catch (DivideZeroException | ResultUndefinedException | OperationException | InvalidInputException e) {
            e.printStackTrace();
        }



        assertBinary(x, percent, resultExpected);
        assertPercentInvalid();
    }

    private void assertBinary (BigDecimal x, BigDecimal percent, BigDecimal resultExpected) {

        binary.setNumberFirst(x);
        binary.setNumberSecond(percent);
        binary.setOperation(OperationsEnum.PERCENT);
        try {
            binary.calculateBinary();
        } catch (ResultUndefinedException | DivideZeroException | OperationException e) {
            e.printStackTrace();
        }
        BigDecimal resultActual = binary.getResult();
        assertEquals(x.multiply(percent.divide(BigDecimal.valueOf(100), MathContext.DECIMAL128)), resultActual);
    }

    private void assertPercentInvalid () {
        binary.setNumberFirst(null);
        binary.setNumberSecond(null);

        try {
            binary.setOperation(OperationsEnum.PERCENT);
            binary.calculateBinary();
        } catch (ResultUndefinedException | DivideZeroException | OperationException e) {
            e.printStackTrace();
        }
    }
}