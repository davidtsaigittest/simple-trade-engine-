# simple-trade-engine-
A simple trade engine

此應用以spring boot作為webapp框架提供一簡易trade engine，透過HTTP對外提供服務
/add 下單進行買或賣的行為
/getall 查詢所有order
/getbyid 依照orderId進行查詢
OOP設計思維:

系統擴充性考量:
可能衍伸出多種Order，且都有各自不同行為，因此將Order抽象並先實作出LimitOrder
OrderService可能回有更多種操作因此將每一種特性使用單一interface宣告後實作

Builder:
將Order物件的建立行為封裝進Builder內，並指允許調用方透過Buiilder.build()建立Order物件

資料結構選用與multi-thread併發問題:
因Java內LinkedList實作了Dequeue，且使用上會平凡異動資料因此選用LinkedList乘載交易資料，並使用synchronized修飾解決thread safe問題

此應用若考慮到併發及可用性可設計為一交易系統如下
1. 將目前婦誤拆分為order-service, match-service兩個服務
2. 服務間的非同步操作可使用Message queue，確保不會因為服務中斷而丟失交易
3. 為提升client使用體驗會改為使用websocke發送通知
4. 後續關於系統架構上的規劃及實作會持續更新
![Trade Engine](https://user-images.githubusercontent.com/61384601/186358562-f029d741-a301-4113-a391-93e4987df85b.png)
