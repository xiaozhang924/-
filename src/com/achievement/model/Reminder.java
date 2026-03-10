    /**
 * Reminder.java
 * 该类用于定义提醒数据模型
 */
package com.achievement.model;

    import java.util.Date;

    public class Reminder {
        private Integer id;
        private Integer userId;
        private String title;
        private String content;
        private Date reminderTime;
        private boolean isRead;
        private Date createdAt;
        private Date updatedAt;

        public Reminder() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getReminderTime() {
            return reminderTime;
        }

        public void setReminderTime(Date reminderTime) {
            this.reminderTime = reminderTime;
        }

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        @Override
        public String toString() {
            return "Reminder{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", reminderTime=" + reminderTime +
                    ", isRead=" + isRead +
                    '}';
        }
    }
