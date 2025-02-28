// Common method to calculate the dates of the week
const getWeekDates = (date) => {
  const week = []
  const curr = new Date(date)
  const dayOfWeek = curr.getDay()

  const firstDayOfWeek = new Date(curr) //will be set as date of Monday
  const offset = dayOfWeek === 0 ? -6 : 1 - dayOfWeek // 0(Sunday) - 6 = Monday, 1 - 2(tuesday) = Monday...
  firstDayOfWeek.setDate(curr.getDate() + offset)

  for (let i = 0; i < 7; i++) {
    const day = new Date(firstDayOfWeek)
    day.setDate(firstDayOfWeek.getDate() + i)
    week.push(day)
  }

  return week
}

// Returns the days (DD) of the week (Monday to Sunday)
export const weekOnlyDate = (date) => {
  const weekDates = getWeekDates(date)
  return weekDates.map((day) => day.toISOString().slice(8, 10)) // Extract day (DD) part
}

// Returns the full date objects (Date) for the week (Monday to Sunday)
export const weekInDateFormate = (date) => {
  return getWeekDates(date) // Returns the complete Date objects array
}

// Return the date in YYYY-MM-DD formate
export const weekInYMD = (date) => {
  const weekDates = getWeekDates(date)
  return weekDates.map((day) => day.toISOString().slice(0, 10))
}

// YYYY-MM-DD
export const getTodayDate = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

// HH:MM
export const getTodayTime = () => {
  const now = new Date()
  const hour = String(now.getHours()).padStart(2, '0')
  const min = String(now.getMinutes()).padStart(2, '0')
  return `${hour}:${min}`
}
