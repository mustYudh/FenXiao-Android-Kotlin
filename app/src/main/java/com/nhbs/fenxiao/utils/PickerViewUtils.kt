package com.nhbs.fenxiao.utils

import android.content.Context
import android.graphics.Color
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson
import com.nhbs.fenxiao.module.mine.bean.JsonBean
import org.json.JSONArray
import java.util.ArrayList

/**
 * @author yudneghao
 * @date 2019-08-04
 */
object PickerViewUtils {
  private var options1Items: List<JsonBean>? = null
  private var options2Items: MutableList<ArrayList<String>>? = null
  private var options3Items: MutableList<ArrayList<ArrayList<String>>>? = null


  public fun showSelectCity(context: Context,
      result: (province: String, city: String, district: String) -> Unit) {// 弹出选择器
    val pvOptions = OptionsPickerBuilder(context,
        OnOptionsSelectListener { options1, options2, options3, v ->
          //返回的分别是三个级别的选中位置
          val opt1tx = if (options1Items!!.isNotEmpty())
            options1Items?.get(options1)!!.pickerViewText else
            ""
          val opt2tx = if (options2Items!!.size > 0 && options2Items!!.get(options1).size > 0)
            options2Items!!.get(options1)[options2] else ""
          val opt3tx = if (options2Items!!.size > 0
              && options3Items!!.get(options1).size > 0
              && options3Items!!.get(options1)[options2].size > 0)
            options3Items!!.get(options1)[options2][options3] else ""
          val tx = opt1tx + opt2tx + opt3tx
          result(opt1tx,opt2tx,opt3tx)
        })

        .setTitleText("城市选择")
        .setDividerColor(Color.BLACK)
        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
        .setContentTextSize(20)
        .build<Any>()

    /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
    pvOptions.setPicker(options1Items, options2Items as List<List<String>>?, options3Items!! as List<List<List<String>>>)//三级选择器
    pvOptions.show()
  }



  public fun initJsonData(context: Context) {
    options1Items = ArrayList()
    options2Items = ArrayList<ArrayList<String>>()
    options3Items = ArrayList<ArrayList<ArrayList<String>>>()
    //解析数据
    /**
     * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
     * 关键逻辑在于循环体
     *
     */
    val JsonData = GetJsonDataUtil().getJson(context, "province.json")//获取assets目录下的json文件数据

    val jsonBean = parseData(JsonData)//用Gson 转成实体

    /**
     * 添加省份数据
     *
     * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
     * PickerView会通过getPickerViewText方法获取字符串显示出来。
     */
    options1Items = jsonBean

    for (i in jsonBean.indices) {//遍历省份
      val cityList = ArrayList<String>()//该省的城市列表（第二级）
      val province_AreaList = ArrayList<ArrayList<String>>()//该省的所有地区列表（第三极）

      for (c in 0 until jsonBean.get(i).getCityList().size) {//遍历该省份的所有城市
        val cityName = jsonBean.get(i).getCityList().get(c).getName()
        cityList.add(cityName)//添加城市
        val city_AreaList = ArrayList<String>()//该城市的所有地区列表

        //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
        /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
        city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea())
        province_AreaList.add(city_AreaList)//添加该省所有地区数据
      }

      /**
       * 添加城市数据
       */
      options2Items?.add(cityList)

      /**
       * 添加地区数据
       */
      options3Items?.add(province_AreaList)
    }

  }


  private fun parseData(result: String): ArrayList<JsonBean> {//Gson 解析
    val detail = ArrayList<JsonBean>()
    try {
      val data = JSONArray(result)
      val gson = Gson()
      for (i in 0 until data.length()) {
        val entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean::class.java)
        detail.add(entity)
      }
    } catch (e: Exception) {
      e.printStackTrace()

    }

    return detail
  }

}