# antifraud_model_automation
通信欺诈治理模型识别自动化
# 预览
![image](https://github.com/ChinaUnicomRI/antifraud_model_automation/blob/master/img/sequence_diag.png)
# 技术栈
- **ch.ethz.ssh2**： 是用纯Java实现SSH-2协议的一个lib包，可以利用它直接在Java程序中连接SSH以及远程拷贝至其他服务器。
# 核心功能
- **定时器**
#
可以根据需求进行每日定时或者指定每月某一天的定时启动
#
- **日志**
#
根据时间记录所有操作，用于回溯分析
#
- **处理任务**
#
启动以及执行处理任务/脚本，根据需求进行不同的处理流程
#
- **调度器**
#
调度处理任务，可以配合定时器执行更加复杂的业务处理要求
#
