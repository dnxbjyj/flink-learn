# DataSet API

# 几种常见的操作
### flat map
- 操作语义：把单条输入数据，经过处理，返回0条、1条或多条数据

### union
- 操作语义：把两个具有相同数据类型数据集合并在一起，返回两个数据集的总和。

### first N
- 操作语义：获取集合中的前N个元素。

### join
- 操作语义：把两个数据集根据关联字段，合并成一个数据集，类似于SQL的join语句。
- 基本操作链：joinedDataset = dataset1.join(dataset2).where(keyselector1).equalTo(keyselector2).with(joinfunction);

### distinct
- 操作语义：去重。
