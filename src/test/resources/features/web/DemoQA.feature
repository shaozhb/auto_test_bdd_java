@Web
Feature: DemoQA图书列表页面测试
  Scenario: 打开图书列表页面，校验页面标题
    Given 打开图书列表页面
    When 获取页面标题
    Then 页面标题包含 "demosite"

  Scenario: 在图书页面搜索关键词，校验过滤结果
    Given 打开图书列表页面
    When 在搜索框输入 "JavaScript"
    Then 页面包含 "JavaScript"
