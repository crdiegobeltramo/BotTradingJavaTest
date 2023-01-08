/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bottradig10;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import java.util.List;

/**
 *
 * @author diego
 */
public class BotTradig10 {



    public static void main(String[] args) {
        // Crea una nueva instancia de BinanceApiRestClient
        //BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        //BinanceApiRestClient client = factory.newRestClient();
        // Crea una nueva instancia de BinanceApiRestClient utilizando tus credenciales de Binance
       String apiKey = "TU_CLAVE_API";
       String apiSecret = "TU_CODIGO_SECRETO";
       BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey, apiSecret);
       BinanceApiRestClient client = factory.newRestClient();


        // Establece el par de divisas y el intervalo de tiempo a utilizar para calcular la media móvil
        String symbol = "BTCUSDT";
        CandlestickInterval interval = CandlestickInterval.HOUR;

        // Obtiene los últimos 500 candeleros para el par de divisas y el intervalo especificados
        List<Candlestick> candlesticks = client.candlesticks(symbol, interval, 500, null, null);

        // Calcula las medias móviles para las últimas 50 y 100 velas
        List<Double> ma50 = MovingAverageCalculator.calculate(candlesticks, 50);
        List<Double> ma100 = MovingAverageCalculator.calculate(candlesticks, 100);

        // Itera a través de las velas más recientes y verifica si se ha producido un cruce de medias móviles
        for (int i = 0; i < candlesticks.size(); i++) {
            Candlestick candle = candlesticks.get(i);

            // Obtiene el precio de cierre de la vela actual
            double close = candle.getClose();

            // Obtiene las medias móviles para la vela actual
            double ma50Value = ma50.get(i);
            double ma100Value = ma100.get(i);

            // Verifica si se ha producido un cruce de medias móviles
            if (ma50Value > ma100Value && ma50Value > close) {
                // Se ha producido un cruce alcista, enviar orden de compra
                sendBuyOrder(symbol, close);
            } else if (ma50Value < ma100Value && ma50Value < close) {
                // Se ha producido un cruce bajista, enviar orden de venta
                sendSellOrder(symbol, close);
            }
        }
    }
}

class MovingAverageCalculator {

    public static List<Double> calculate(List<Candlestick> candlesticks, int period) {
        // Implementación del cálculo de la media móvil
        // ...
    }
}
