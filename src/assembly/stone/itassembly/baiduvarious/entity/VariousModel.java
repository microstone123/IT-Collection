package assembly.stone.itassembly.baiduvarious.entity;

import java.io.Serializable;
import java.util.List;

public class VariousModel implements Serializable {

 /**
  *
  */
 private static final long serialVersionUID = 1L;
 private Integer m_id;
 private String m_title;
 private String m_image_url;
 private String m_display_url;
 private String m_summary;
 private String m_create_time = "";
 private String m_writer_url;
 private String m_writer_name ="";
 private String hotcount;
 private List<LabelNames> m_label_names;

 public Integer getM_id() {
  return m_id;
 }

 public void setM_id(Integer m_id) {
  this.m_id = m_id;
 }

 public String getM_title() {
  return m_title;
 }

 public void setM_title(String m_title) {
  this.m_title = m_title;
 }

 public String getM_image_url() {
  return m_image_url;
 }

 public void setM_image_url(String m_image_url) {
  this.m_image_url = m_image_url;
 }

 public String getM_display_url() {
  return m_display_url;
 }

 public void setM_display_url(String m_display_url) {
  this.m_display_url = m_display_url;
 }

 public String getM_summary() {
  return m_summary;
 }

 public void setM_summary(String m_summary) {
  this.m_summary = m_summary;
 }

 public String getM_create_time() {
  return m_create_time;
 }

 public void setM_create_time(String m_create_time) {
  this.m_create_time = m_create_time;
 }

 public String getM_writer_url() {
  return m_writer_url;
 }

 public void setM_writer_url(String m_writer_url) {
  this.m_writer_url = m_writer_url;
 }

 public String getM_writer_name() {
  return m_writer_name;
 }

 public void setM_writer_name(String m_writer_name) {
  this.m_writer_name = m_writer_name;
 }

 public String getHotcount() {
  return hotcount;
 }

 public void setHotcount(String hotcount) {
  this.hotcount = hotcount;
 }

 public List<LabelNames> getM_label_names() {
  return m_label_names;
 }

 public void setM_label_names(List<LabelNames> m_label_names) {
  this.m_label_names = m_label_names;
 }

 public class LabelNames implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private Integer m_id;
  private String m_name;

  public Integer getM_id() {
   return m_id;
  }

  public void setM_id(Integer m_id) {
   this.m_id = m_id;
  }

  public String getM_name() {
   return m_name;
  }

  public void setM_name(String m_name) {
   this.m_name = m_name;
  }
 }

}
