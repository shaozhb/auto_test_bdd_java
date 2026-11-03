@Android
Feature: API Demos原生应用测试
  Scenario: 进入Accessibility菜单校验页面
    Given 启动API Demos应用
    When 点击 "Accessibility" 菜单
    Then 安卓页面包含文本 "Accessibility"

  Scenario: 进入Animation菜单
    Given 启动API Demos应用
    When 点击 "Animation" 菜单
    Then 安卓页面包含文本 "Bouncing Balls"
